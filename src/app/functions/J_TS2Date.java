package app.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import app.Jmeter_Date;

public class J_TS2Date extends AbstractFunction {
	// 显示的参数名字
	private static final List<String> desc = new LinkedList<>();
	// 显示的函数名字
	private static final String KEY = "__J_TS2Date";
	// 参数值
	private Object[] values;
	static {
		desc.add("String to timeStamp"); // 函数助手中显示的参数说明，对应到参数
		desc.add("String to timeUnit, s or ms"); // 函数助手中显示的参数说明，对应到参数
		desc.add("String to date format"); // 函数助手中显示的参数说明，对应到参数
		desc.add("Name of variable in which to store the result (optional)");// 保存函数返回结果的变量，用于引用
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1)
			throws InvalidVariableException {
		String timeStamp = ((CompoundVariable) this.values[0]).execute();
		String timeUnit = ((CompoundVariable) this.values[1]).execute();
		String format = ((CompoundVariable) this.values[2]).execute();
		String ts2date = Jmeter_Date.ts2date(timeStamp, timeUnit, format);
		return ts2date;
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
		// 检查参数数量
		checkParameterCount(arg0, 4);
		this.values = arg0.toArray();
	}
}