local mtable = {}
local tbl = setmetatable({ a=2, b=4 }, mtable)

mtable.__index = mtable

function mtable:stuff(str)
    print(self.a + self.b, str)
end

print(tbl:stuff('bitch'))