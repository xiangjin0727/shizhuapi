package com.hz.app.api.util.security;

import flexjson.Transformer;

/**
 * flexjson转换器
 */
public class JsonTransformer implements Transformer {

	@Override
	public String transform(Object obj) {
		if (null == obj) {
			return null;
		}
		String str = obj.toString();
		String rtn = TextFilter.doFilte(str);

		return rtn;
	}
}
