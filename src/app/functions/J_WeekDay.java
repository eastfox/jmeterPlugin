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

public class J_WeekDay extends AbstractFunction {
	// 显示的参数名字
	private static final List<String> desc = new LinkedList<>();
	// 显示的函数名字
	private static final String KEY = "__J_WeekDay";
	// 参数值
	private Object[] values;
	static {
		desc.add("String to Date"); // 函数助手中显示的参数说明，对应到参数
		desc.add("String to Date format"); // 函数助手中显示的参数说明，对应到参数
		desc.add("String to WeekDay type, Mon, Tue, Wed, Thu, Fri, Sat, Sun"); // 函数助手中显示的参数说明，对应到参数
		desc.add("Name of variable in which to store the result (optional)");// 保存函数返回结果的变量，用于引用
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1)
			throws InvalidVariableException {
		String date = ((CompoundVariable) this.values[0]).execute();
		String format = ((CompoundVariable) this.values[1]).execute();
		String weekDay = ((CompoundVariable) this.values[2]).execute();
		String dateOfWeekDay = null;
		dateOfWeekDay = Jmeter_Date.dateOfWeekDay(date, format, weekDay);
		return dateOfWeekDay;
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