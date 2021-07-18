package com.hk.str;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.hk.math.ComparatorUtil;

public class HTMLText
{
    private final StringBuilder sb;
    private final Map<Integer, Set<String>> varPositions;
    private final Map<String, String> variables;
    private boolean blockWS;
    private int tabs;
    
    public HTMLText()
    {
        varPositions = new TreeMap<>(ComparatorUtil.reversed(ComparatorUtil.compInteger));
        variables = new LinkedHashMap<>();
        sb = new StringBuilder();
        blockWS = false;
        tabs = 0;
    }
    
    public HTMLText tabUp()
    {
        tabs++;
        return this;
    }
    
    public HTMLText tabDown()
    {
        tabs--;
        return this;
    }
    
    public HTMLText wr(String s)
    {
        sb.append(s);
        return this;
    }
    
    public HTMLText wrln(String s)
    {
        return wr(s).ln();
    }
    
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
    
    public HTMLText ln()
    {
        sb.append(blockWS ? ' ' : '\n');
        return this;
    }
    
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
    
    public HTMLText openBrace()
    {
        return tabs().wrln("{").tabUp();
    }
    
    public HTMLText closeBrace()
    {
        return tabDown().tabs().wrln("}");
    }
    
    public HTMLText pr(String s)
    {
        return tabs().wr(s);
    }
    
    public HTMLText prln(String s)
    {
        return tabs().wrln(s);
    }
    
    public HTMLText open(String tag, String... attrs)
    {
        return pr("<" + tag).appendAttrs(attrs).wrln(">").tabUp();
    }
    
    public HTMLText close(String tag)
    {
        return tabDown().pr("</").wr(tag).wr(">").ln();
    }
    
    public HTMLText el(String tag, String html, String... attrs)
    {
        pr("<" + tag);
        appendAttrs(attrs);
        return html == null ? wrln("/>") : wr(">").wr(html).wr("</").wr(tag).wrln(">");
    }
    
    private HTMLText appendAttrs(String[] attrs)
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
                	wr("name=\"").wr(val).wr("\" id=\"").wr(val).wr("\"");
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
    
    public HTMLText makeVar(String name)
    {
        return makeVar(name, null);
    }
    
    public HTMLText makeVar(String name, String value)
    {
        if(!hasVar(name))
        {
            int index = sb.length();
            Set<String> vars = varPositions.get(index);
            if(vars == null)
            {
                vars = new LinkedHashSet<>();
                varPositions.put(index, vars);
            }
            vars.add(name);
            variables.put(name, value);
        }
        return this;
    }
    
    public HTMLText setVar(String name, String value)
    {
        if(hasVar(name))
        {
            variables.put(name, value);
        }
        return this;
    }
    
    public String[] getVars()
    {
        return variables.keySet().toArray(new String[0]);
    }
    
    public String getVar(String name)
    {
        return variables.get(name);
    }
    
    public boolean hasVar(String name)
    {
        return variables.containsKey(name);
    }
    
    public HTMLText blockWS(boolean def)
    {
        blockWS = def;
        return this;
    }
    
    public boolean isBlocking()
    {
        return blockWS;
    }
    
    public String create()
    {
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
   
}
