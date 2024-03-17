package ReflectionExplore;

import java.lang.reflect.*;

interface Evaluatable {
	double evalf(double x);
}

public abstract class Task5 {

	public static void main(String[] args) {
		Evaluatable f1 = new Function1();
		Evaluatable f2 = new Function2();

		Evaluatable proxyF1 = (Evaluatable) Proxy.newProxyInstance(Task5.class.getClassLoader(),
				new Class[] { Evaluatable.class }, new MethodProfiler(f1));

		Evaluatable proxyF2 = (Evaluatable) Proxy.newProxyInstance(Task5.class.getClassLoader(),
				new Class[] { Evaluatable.class }, new MethodProfiler(f2));

		double x = 1.0;

		System.out.println("F1: " + proxyF1.evalf(x));
		System.out.println("F2: " + proxyF2.evalf(x));
	}
}

class Function1 implements Evaluatable {
	@Override
	public double evalf(double x) {
		return Math.exp(-Math.abs(2.5 * x)) * Math.sin(x);
	}
}

class Function2 implements Evaluatable {
	@Override
	public double evalf(double x) {
		return x * x;
	}
}

class MethodProfiler implements InvocationHandler {
	private final Evaluatable target;

	public MethodProfiler(Evaluatable target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		long start = System.nanoTime();
		Object result = method.invoke(target, args);
		long end = System.nanoTime();

		System.out.println("[" + method.getName() + "] took " + (end - start) + " ns");

		if (args != null && args.length > 0) {
			System.out.println("[" + method.getName() + "]" + "(" + args[0] + ") = " + result);
		}

		return result;
	}
}
