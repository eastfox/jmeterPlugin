package app.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import app.Jmeter_HMACSHA1;

public class J_HMACSHA1 extends AbstractFunction {
	// 显示的参数说明
	private static final List<String> desc = new LinkedList<>();
	// 显示的函数名字
	private static final String KEY = "__J_HMACSHA1";
	// 参数值
	private Object[] values;
	// 添加参数说明
	static {
		desc.add("String to HMACSHA1 key"); // 函数助手中显示的参数说明，对应到参数
		desc.add("String to calculate HMACSHA1 hash");
		desc.add("Name of variable in which to store the result (optional)");// 保存函数返回结果的变量，用于引用
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1)
			throws InvalidVariableException {
		String key = ((CompoundVariable) this.values[0]).execute();
		String data = ((CompoundVariable) this.values[1]).execute();
		String HMACSHA1 = Jmeter_HMACSHA1.computeSignature(key, data);
		return HMACSHA1;
	}

	@Override
	public List<String> getArgumentDesc() {
		return desc;
	}

	@Override
	public String getReferenceKey() {
		return KEY;
	}

	@Override
	public void setParameters(Collection<CompoundVariable> arg0)
			throws InvalidVariableException {
		checkParameterCount(arg0, 3);
		this.values = arg0.toArray();

	}

}
