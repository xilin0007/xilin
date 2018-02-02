
/**
 * 注解：
 * @interface用来声明一个注解，其中的每一个方法实际上是声明了一个配置参数。
 * 方法的名称就是参数的名称，返回值类型就是参数的类型
 *（返回值类型只能是基本类型、Class、String、enum）。
 * 可以通过default来声明参数的默认值。
 * @Target(ElementType.PACKAGE)//只能用在package-info上
 */
package com.annotation;
