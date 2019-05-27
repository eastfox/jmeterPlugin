package app.functions;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import app.Jmeter_WriteFile;

public class J_WriteFile extends AbstractFunction {
	// 显示的参数名字
	private static final List<String> desc = new LinkedList<>();
	// 显示的函数名字
	private static final String KEY = "__J_WriteFile";
	// 参数值
	private Object[] values;
	static {
		desc.add("String to filePath and fileName"); // 函数助手中显示的参数说明，对应到参数
		desc.add("String to content to write");
		desc.add("String to write method (o)verwrite or (a)ppend");
		desc.add("Name of variable in which to store the result (optional)");// 保存函数返回结果的变量，用于引用
	}

	@Override
	public List<String> getArgumentDesc() {
		return desc;
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1)
			throws InvalidVariableException {
		String fileName = ((CompoundVariable) this.values[0]).execute();
		String content = ((CompoundVariable) this.values[1]).execute();
		String method = ((CompoundVariable) this.values[2]).execute();
		try {
			Jmeter_WriteFile.writeFile(fileName, content, method);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
