package app.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import app.Jmeter_CHN;

public class J_CHN extends AbstractFunction {
	// 显示的参数名字
	private static final List<String> desc = new LinkedList<>();
	// 显示的函数名字
	private static final String KEY = "__J_CHN";

	static {
		desc.add("Name of variable in which to store the result (optional)");// 保存函数返回结果的变量，用于引用
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1)
			throws InvalidVariableException {
		String CHN = Jmeter_CHN.getChineseAll();
		return CHN;
	}

	@Override
	public String getReferenceKey() {
		return KEY;
	}

	@Override
	public List<String> getArgumentDesc() {
		return desc;
	}

	@Override
	public void setParameters(Collection<CompoundVariable> arg0)
			throws InvalidVariableException {
		// 检查参数数量
		checkParameterCount(arg0, 0);

	}

}
