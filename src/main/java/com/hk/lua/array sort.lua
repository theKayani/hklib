if not arr then
  arr = { 44,	24,	3, 55, 19, 21, 15,	17,	52,	38, 98,	14,	85,	26,	16 }
end

for i = 1, #arr - 1 do
    for j = i + 1, #arr do
        if arr[i] > arr[j] then
            local tmp = arr[i]
            arr[i] = arr[j];
            arr[j] = tmp
        end
    end
end

return arr