package com.hk.str;

import java.util.*;

import com.hk.math.ComparatorUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>HTMLText class.</p>
 *
 * @author theKayani
 */
public class HTMLText
{
    private final StringBuilder sb;
    private final Map<Integer, Set<String>> varPositions;
    private final Map<String, String> variables;
    private boolean blockWS;
    private int tabs;

    /**
     * <p>Constructor for HTMLText.</p>
     */
    public HTMLText()
    {
        varPositions = new TreeMap<>(ComparatorUtil.reversed(ComparatorUtil.compInteger));
        variables = new LinkedHashMap<>();
        sb = new StringBuilder();
        blockWS = false;
        tabs = 0;
    }

    /**
     * <p>tabUp.</p>
     *
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText tabUp()
    {
        tabs++;
        return this;
    }

    /**
     * <p>tabDown.</p>
     *
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText tabDown()
    {
        tabs--;
        return this;
    }

    /**
     * <p>wr.</p>
     *
     * @param s a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText wr(@NotNull String s)
    {
        sb.append(s);
        return this;
    }

    /**
     * <p>wrln.</p>
     *
     * @param s a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText wrln(@NotNull String s)
    {
        return wr(s).ln();
    }

    /**
     * <p>tabs.</p>
     *
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText tabs()
    {
        if(!blockWS)
        {
            sb.ensureCapacity(sb.length() + tabs);
            for(int i = 0; i < tabs; i++)
                sb.append('\t');
        }
        return this;
    }

    /**
     * <p>ln.</p>
     *
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText ln()
    {
        sb.append(blockWS ? ' ' : '\n');
        return this;
    }

    /**
     * <p>br.</p>
     *
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText br()
    {
        int i = sb.length() - 1;
        for(; i >= 0; i--)
        {
            if(sb.charAt(i) != '\n')
            {
                i++;
                break;
            }
        }
        sb.setLength(i);
        return wrln("<br/>");
    }

    /**
     * <p>openBrace.</p>
     *
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText openBrace()
    {
        return tabs().wrln("{").tabUp();
    }

    /**
     * <p>closeBrace.</p>
     *
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText closeBrace()
    {
        return tabDown().tabs().wrln("}");
    }

    /**
     * <p>pr.</p>
     *
     * @param s a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText pr(@NotNull String s)
    {
        return tabs().wr(s);
    }

    /**
     * <p>prln.</p>
     *
     * @param s a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText prln(@NotNull String s)
    {
        return tabs().wrln(s);
    }

    /**
     * <p>open.</p>
     *
     * @param tag a {@link java.lang.String} object
     * @param attrs a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText open(@NotNull String tag, String... attrs)
    {
        return pr("<" + tag).appendAttrs(attrs).wrln(">").tabUp();
    }

    /**
     * <p>close.</p>
     *
     * @param tag a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText close(@NotNull String tag)
    {
        return tabDown().pr("</").wr(tag).wr(">").ln();
    }

    /**
     * <p>el.</p>
     *
     * @param tag a {@link java.lang.String} object
     * @param html a {@link java.lang.String} object
     * @param attrs a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText el(@NotNull String tag, @Nullable String html, String... attrs)
    {
        pr("<" + tag);
        appendAttrs(attrs);
        return html == null ? wrln("/>") : wr(">").wr(html).wr("</").wr(tag).wrln(">");
    }

    @NotNull
    private HTMLText appendAttrs(@NotNull String[] attrs)
    {
    	int len = attrs.length;
        if(len > 0)
        {
            String key, val;
            wr(" ");
            for(int i = 0; i < len; i += 2)
            {
                key = attrs[i];
                val = i < len - 1 ? attrs[i + 1] : null;
                if("nid".equals(key))
                	wr("name=\"").wr(Objects.requireNonNull(val)).wr("\" id=\"").wr(val).wr("\"");
                else if(key != null)
                {
                	if(key.startsWith("x-ng-") && key.length() > 5)
                		wr("data" + key.substring(1));
                	else if(key.startsWith("~"))
                		wr("data-ng-" + key.substring(1));
                	else
                		wr(key);

                    if(val != null)
                        wr("=\"").wr(val).wr("\"");
                }
                wr(" ");
            }
            sb.setLength(sb.length() - 1);
        }

        return this;
    }

    /**
     * <p>makeVar.</p>
     *
     * @param name a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText makeVar(@NotNull String name)
    {
        return makeVar(name, null);
    }

    /**
     * <p>makeVar.</p>
     *
     * @param name a {@link java.lang.String} object
     * @param value a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText makeVar(@NotNull String name, @Nullable String value)
    {
        if(!hasVar(name))
        {
            int index = sb.length();
            Set<String> vars = varPositions.computeIfAbsent(index, k -> new LinkedHashSet<>());
            vars.add(name);
            variables.put(name, value);
        }
        return this;
    }

    /**
     * <p>setVar.</p>
     *
     * @param name a {@link java.lang.String} object
     * @param value a {@link java.lang.String} object
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText setVar(@NotNull String name, @NotNull String value)
    {
        if(hasVar(name))
            variables.put(name, value);

        return this;
    }

    /**
     * <p>getVars.</p>
     *
     * @return an array of {@link java.lang.String} objects
     */
    @NotNull
    public String[] getVars()
    {
        return variables.keySet().toArray(new String[0]);
    }

    /**
     * <p>getVar.</p>
     *
     * @param name a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    @Nullable
    public String getVar(@NotNull String name)
    {
        return variables.get(name);
    }

    /**
     * <p>hasVar.</p>
     *
     * @param name a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean hasVar(@NotNull String name)
    {
        return variables.containsKey(name);
    }

    /**
     * <p>blockWS.</p>
     *
     * @param def a boolean
     * @return a {@link com.hk.str.HTMLText} object
     */
    @NotNull
    public HTMLText blockWS(boolean def)
    {
        blockWS = def;
        return this;
    }

    /**
     * <p>isBlocking.</p>
     *
     * @return a boolean
     */
    public boolean isBlocking()
    {
        return blockWS;
    }

    /**
     * <p>create.</p>
     *
     * @return a {@link java.lang.String} object
     */
    @NotNull
    public String create()
    {
        StringBuilder sb = new StringBuilder(this.sb);
        Set<Map.Entry<Integer, Set<String>>> set = varPositions.entrySet();
        int index;
        String value;
        Set<String> vars;
        for(Map.Entry<Integer, Set<String>> e : set)
        {
            index = e.getKey();
            vars = e.getValue();
            for(String var : vars)
            {
                value = variables.get(var);
                if(value != null)
                {
                    sb.insert(index, value);
                    index += value.length();
                }
            }
        }
        return sb.toString().trim();
    }

    /** {@inheritDoc} */
    @Override
    @NotNull
    public String toString()
    {
        return create();
    }
}