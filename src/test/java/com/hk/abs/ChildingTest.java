package com.hk.abs;

import com.hk.func.Consumer;
import junit.framework.TestCase;

import java.util.Stack;

public class ChildingTest extends TestCase
{
	public void testHTMLTag()
	{
		HTMLTag head = new HTMLTag("head", new HTMLTag("title"));
		HTMLTag body = new HTMLTag("body", new HTMLTag("div"));
		final HTMLTag html = new HTMLTag("html", head, body);

		final Stack<String> expectedTags = new Stack<>();
		Consumer<HTMLTag> csm = new Consumer<HTMLTag>()
		{
			@Override
			public void accept(HTMLTag htmlTag)
			{
				assertEquals(expectedTags.pop(), htmlTag.tag);
			}
		};

		expectedTags.push("div");
		expectedTags.push("title");
		expectedTags.push("body");
		expectedTags.push("head");
		breadthFirstLoop(html, csm);

		expectedTags.push("div");
		expectedTags.push("body");
		expectedTags.push("title");
		expectedTags.push("head");
		depthFirstLoop(html, csm);
	}

	static void breadthFirstLoop(HTMLTag root, Consumer<HTMLTag> consumer)
	{
		if(root != null)
		{
			HTMLTag[] children = root.getChildren();

			for(HTMLTag child : children)
			{
				if(child != null)
					consumer.accept(child);
			}

			for(HTMLTag child : children)
				breadthFirstLoop(child, consumer);
		}
	}

	static void depthFirstLoop(HTMLTag root, Consumer<HTMLTag> consumer)
	{
		if (root != null)
		{
			HTMLTag[] children = root.getChildren();

			for(HTMLTag child : children)
			{
				if(child != null)
					consumer.accept(child);
				depthFirstLoop(child,  consumer);
			}
		}
	}

	static class HTMLTag implements Childing<HTMLTag>
	{
		final String tag;
		private HTMLTag parent;
		final HTMLTag[] children;

		HTMLTag(String tag, HTMLTag... children)
		{
			this.tag = tag;
			this.children = children;

			for(HTMLTag child : children)
				child.parent = this;
		}

		public HTMLTag getParent()
		{
			return parent;
		}

		public HTMLTag[] getChildren()
		{
			return children;
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append('<').append(tag).append('>');

			for(HTMLTag child : children)
				sb.append(child);

			sb.append("</").append(tag).append('>');
			return sb.toString();
		}
	}
}
