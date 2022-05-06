package com.hk.abs;

import org.jetbrains.annotations.Nullable;

/**
 * <p>This interface is used to refer to a class which can contain a
 * number of children objects similar to it. Let's use HTML tags as
 * an example, here:</p>
 * <pre>
 *     class HTMLTag implements Childing&lt;HTMLTag&gt;
 *     {
 *         final String tag;
 *         private HTMLTag parent;
 *         final HTMLTag[] children;
 *
 *         HTMLTag(String tag, HTMLTag... children)
 *         {
 *             this.tag = tag;
 *             this.children = children;
 *
 *             for(HTMLTag child : children)
 *                 child.parent = this;
 *         }
 *
 *         public HTMLTag getParent()
 *         {
 *             return parent;
 *         }
 *
 *         public HTMLTag[] getChildren()
 *         {
 *             return children;
 *         }
 *
 *         &#64;Override
 *         public String toString()
 *         {
 *             StringBuilder sb = new StringBuilder();
 * 	           sb.append('&lt;').append(tag).append('&gt;');
 *
 * 			   for(HTMLTag child : children)
 * 				   sb.append(child);
 *
 * 			   sb.append("&lt;/").append(tag).append('&gt;');
 * 			   return sb.toString();
 *         }
 *     }</pre>
 * <p>Then a section, or an entire, HTML document can be defined using
 * this class here with the appropriate constructor. The benefit to
 * having this interface is you can extend HTMLTag with a custom
 * class, such as... <code>SpanTag</code> or <code>DivTag</code> or
 * even <code>StyledTag</code> which can potentially take property
 * key and value pairs.</p>
 *
 * <p>Having it this way can introduce various different mechanics and
 * work flows upon data, in an Object-Oriented manner. Also making
 * looping through said data in an easily implemented recursive
 * manner. Let me explain...</p>
 *
 * <p>Here is an example of two different methods to loop through a
 * <code>Childing</code> object. Using either breadth first or
 * depth first search.</p>
 * <pre>
 *     static void breadthFirstLoop(HTMLTag root, Consumer&lt;HTMLTag&gt; consumer)
 *     {
 *         if(root != null)
 *         {
 *             HTMLTag[] children = root.getChildren();
 *
 *             for(HTMLTag child : children)
 *             {
 *                 if(child != null)
 *                     consumer.accept(child);
 *             }
 *
 *             for(HTMLTag child : children)
 *                 breadthFirstLoop(child, consumer);
 *         }
 *     }
 *
 *     static void depthFirstLoop(HTMLTag root, Consumer&lt;HTMLTag&gt; consumer)
 *     {
 *         if (root != null)
 *         {
 *             HTMLTag[] children = root.getChildren();
 *
 *             for(HTMLTag child : children)
 *             {
 *                 if(child != null)
 *                     consumer.accept(child);
 *                 depthFirstLoop(child,  consumer);
 *             }
 *         }
 *     }</pre>
 * <p>Now let's say we have the following HTMLTag...</p>
 * <pre>
 * 	   HTMLTag head = new HTMLTag("head", new HTMLTag("title"));
 * 	   HTMLTag body = new HTMLTag("body", new HTMLTag("div"));
 * 	   final HTMLTag html = new HTMLTag("html", head, body);</pre>
 * <p>Using our breadth first method will produce:
 * <code>html-&gt;head-&gt;body-&gt;title-&gt;div</code></p>
 * <p>Whereas our depth first method will produce:
 * <code>html-&gt;head-&gt;title-&gt;body-&gt;div</code></p>
 * <br>
 * <p><i>Neat!</i></p>
 *
 * @author theKayani
 */
public interface Childing<T extends Childing<T>>
{
	/**
	 * Get the parent object of this object. Can be null if this
	 * represents the root object.
	 *
	 * @return the parent object of this object of the <code>T</code> type.
	 */
	@Nullable
	T getParent();

	/**
	 * Get an array of children objects that belong to this parent
	 * object. In theory, their {@link #getParent()} method would
	 * return this object. This array of children could be null, or
	 * have a length of 0, indicating no child elements. The
	 * returned array shouldn't contain any null values.
	 *
	 * @return an array of child T[] objects, or null if there are no
	 * children objects to this one.
	 */
	@Nullable
	T[] getChildren();
}
