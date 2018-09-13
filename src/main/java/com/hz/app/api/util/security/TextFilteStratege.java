package com.hz.app.api.util.security;

/**
 * 文本过滤策略
 */
public class TextFilteStratege {
	private Integer start;// 起始位置
	private Integer end;// 结束位置,可为负（负数时从右向左数）
	private Integer realEnd;// 真实结束位置
	private Integer total;// 总长度

	public TextFilteStratege(Integer total, Integer start, Integer end) {
		super();
		this.total = total;
		this.start = start;
		this.end = end;

		if (end > 0) {
			this.realEnd = end;
		} else {
			int tmpEnd = total + end;
			if (tmpEnd < 0) {
				tmpEnd = 1;
			}
			this.realEnd = tmpEnd;
		}
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "TextFilteStratege [start=" + start + ", end=" + end
				+ ", total=" + total + "]";
	}

	public Integer getRealEnd() {
		return realEnd;
	}

	public void setRealEnd(Integer realEnd) {
		this.realEnd = realEnd;
	}
}
