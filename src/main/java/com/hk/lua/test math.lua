print("testing math and math library")
huge = math.huge
maxint, minint = math.maxinteger, math.mininteger

-- float equality
function eq (a,b,limit)
  return a == b or math.abs(a-b) <= (limit or 1E-11)
end


-- equality with types
function eqT(a, b)
  return a == b and math.type(a) == math.type(b)
end

-- math.abs (x)
do
  assert(eqT(math.abs(-2), 2))
  assert(eqT(math.abs(-1), 1))
  assert(eqT(math.abs(0), 0))
  assert(eqT(math.abs(1), 1))
  assert(eqT(math.abs(2), 2))

  assert(eqT(math.abs(-2.0), 2.0))
  assert(eqT(math.abs(-1.0), 1.0))
  assert(eqT(math.abs(-0.0), 0.0))
  assert(eqT(math.abs(0.0), 0.0))
  assert(eqT(math.abs(1.0), 1.0))
  assert(eqT(math.abs(2.0), 2.0))
  
  assert(eqT(math.abs(-maxint), maxint))
  assert(eqT(math.abs(minint + 1), maxint))
  assert(eqT(math.abs(minint), minint)) -- integer overflow
end

-- math.acos (x)
do
  -- assert(eq())
end