/**
 * 早期（编译器）优化
 * 1.插入式注解处理器
 * 2.通过-processor指定编译时需附带的注解处理器
 * 	 javac -processor com.jvm.compile.NameCheckProcessor com/jvm/compile/BADLY_NAMED_CODE.java
 * 
 * 晚期（运行期）优化
 */
package com.jvm.compile;