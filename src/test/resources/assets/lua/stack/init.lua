stacks = {}

stacks.__index = stacks

function stacks.new(o)
    return setmetatable(o or {}, stacks)
end

function stacks.push(self, val)
    if val then
        table.insert(self, 1, val)
    end
    return self
end

function stacks.peek(self)
    return self[1]
end

function stacks.pop(self)
    if #self > 0 then
        return table.remove(self, 1)
    end
end

function stacks.__tostring(self)
    local str = '['
    for _, v in ipairs(self) do
        str = str .. tostring(v) .. ', '
    end
    str = str:sub(1, #str - 2) .. ']'
    return str
end

return stacks