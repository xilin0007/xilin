package com.jvm.compile.early;

import java.util.EnumSet;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner7;
import javax.tools.Diagnostic.Kind;

/**
 * 程序名称规范的编译器插件
 * 如果程序命名不和规范，将会输出一个编译器的WARNING信息
 */
public class NameChecker {
	
	private final Messager messager;
	
	NameCheckScanner nameCheckScanner = new NameCheckScanner();
	
	public NameChecker(ProcessingEnvironment processingEnv) {
		this.messager = processingEnv.getMessager();
	}
	
	/**
	 * 对java程序命名进行检查，包括类接口，方法，字段，变量，常量等
	 */
	public void checkNames(Element element) {
		nameCheckScanner.scan(element);
	}

	/**
	 * 名称检测器实现类，继承JDK1.7中的ElementScanner7
	 * 将会以Visitor模式访问抽象语法树中的元素
	 */
	private class NameCheckScanner extends ElementScanner7<Void, Void> {
		
		/**
		 * 检查java类是否规范
		 */
		@Override
		public Void visitType(TypeElement e, Void p) {
			scan(e.getTypeParameters(), p);
			checkCamelCase(e, true);
			super.visitType(e, p);
			return null;
		}
		
		/**
		 * 检查方法名是否规范
		 */
		@Override
		public Void visitExecutable(ExecutableElement e, Void p) {
			if (e.getKind() == ElementKind.METHOD) {
				Name name = e.getSimpleName();
				if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
					messager.printMessage(Kind.WARNING, "一个普通方法“" + name + "”不应当与类名重复，避免与构造函数产生混淆", e);
					checkCamelCase(e, false);
				}
			}
			super.visitExecutable(e, p);
			return null;
		}

		/**
		 * 检查变量名是否规范
		 */
		@Override
		public Void visitVariable(VariableElement e, Void p) {
			
			if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)) {
				checkAllCaps(e);
			} else {
				checkCamelCase(e, false);
			}
			return null;
		}
		
		private boolean heuristicallyConstant(VariableElement e) {
			if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
				return true;
			} else if (e.getKind() == ElementKind.FIELD && e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))) {
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * 检查传入的Element是否符合驼式命名法，如果不符合，则输出警告信息
		 */
		private void checkCamelCase(Element e, boolean initialCaps) {
			String name = e.getSimpleName().toString();
			boolean previousUpper = false;//首字母是否大写
			boolean conventional = true;//是否符合规范的
			int firstCodePoint = name.codePointAt(0);
			if (Character.isUpperCase(firstCodePoint)) {
				previousUpper = true;
				if (!initialCaps) {
					messager.printMessage(Kind.WARNING, "名称“" + name + "”应当以小写字母开头", e);
					return;
				}
			} else if (Character.isLowerCase(firstCodePoint)) {
				if (initialCaps) {
					messager.printMessage(Kind.WARNING, "名称“" + name + "”应当以大写字母开头", e);
					return;
				}
			} else {
				conventional = false;
			}
			if (conventional) {
				int cp = firstCodePoint;
				for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
					cp = name.codePointAt(i);
					if (Character.isUpperCase(cp)) {
						if (previousUpper) {
							conventional = false;
							break;
						}
						previousUpper = true;
					} else {
						previousUpper = false;
					}
				}
			}
			
			if (!conventional) {
				messager.printMessage(Kind.WARNING, "名称“" + name + "”应当符合驼式命名法（Came Case names）", e);
			}
		}
		
		/**
		 * 大写命名检查，要求第一个字母必须是大写的英文字母，其余的部分可以试下划线或大写字母
		 */
		private void checkAllCaps(Element e) {
			String name = e.getSimpleName().toString();
			boolean conventional = true;//是否符合规范的
			int firstCodePoint = name.codePointAt(0);
			if (!Character.isUpperCase(firstCodePoint)) {
				conventional = false;
			} else {
				boolean previousUnderscore = false;
				int cp = firstCodePoint;
				for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
					cp = name.codePointAt(i);
					if (cp == (int) '_') {
						if (previousUnderscore) {
							conventional = false;
							break;
						}
						previousUnderscore = true;
					} else {
						previousUnderscore = false;
						if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
							conventional = false;
							break;
						}
					}
				}
			}
			if (!conventional) {
				messager.printMessage(Kind.WARNING, "常量“" + name + "”应当全部以大写字母或下划线命名，并且以字母开头", e);
			}
		}
		
	}
}
