package com.hz.core.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CommonTag extends TagSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String className;
	private String value;
	private static final String path = "com.hz.core.framework.enumeration.";

	public CommonTag()
	{}

	@Override
	public int doEndTag() throws JspException
	{
		try
		{
			JspWriter out = this.pageContext.getOut();
			Class<?> c = Class.forName(CommonTag.path + this.className);
			Method m_name = c.getMethod("getName", null);
			Method m_index = c.getMethod("getIndex", null);
			for (int i = 0; i < c.getEnumConstants().length; i++)
			{
				if (this.value.equals(m_index.invoke(c.getEnumConstants()[i],
						null).toString()))
				{
					out.println(m_name.invoke(c.getEnumConstants()[i], null));
				}
			}
		}
		catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	@Override
	public void release()
	{
		super.release();
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
