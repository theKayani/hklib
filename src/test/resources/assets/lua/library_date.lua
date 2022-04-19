-- testing date meta methods
local d, err
local dateFormat = 'yyyy-MM-dd HH:mm:ss'
local dateFormat2 = 'yyMMddHHmmss'
local dateFormat3 = "yyyy.MM.dd G 'at' HH:mm:ss"
assert(date.getformat() == dateFormat)

d = date.now()

assert(math.type(d:year()) == 'integer')
assert(math.type(d:month()) == 'integer')
assert(math.type(d:day()) == 'integer')
assert(math.type(d:hour()) == 'integer')
assert(math.type(d:minute()) == 'integer')
assert(math.type(d:second()) == 'integer')
assert(math.type(d:time()) == 'integer')
assert(d == date.fromtime(d:time()))

assert(tostring(d):find '\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}')
assert(d:format(dateFormat2):find '\\d{12}')
assert(d:format(dateFormat3):find '\\d{4}.\\d{2}.\\d{2} AD at \\d{2}:\\d{2}:\\d{2}')

-- testing longs to dates and dates to longs

assert(d == d:year(2020)); assert(d)
assert(d == d:month(1)); assert(d)
assert(d == d:day(1)); assert(d)
assert(d == d:hour(0)); assert(d)
assert(d == d:minute(0)); assert(d)
assert(d == d:second(0)); assert(d)

d = date.fromtime(d:time() + 30000)

assert(d)
assert(d:day() == 1)
assert(d:month() == 1)
assert(d:year() == 2020)
assert(d:hour() == 0)
assert(d:minute() == 0)
assert(d:second() == 30)

d = date.fromtime(d:time() + 30000)

assert(d)
assert(d:day() == 1)
assert(d:month() == 1)
assert(d:year() == 2020)
assert(d:hour() == 0)
assert(d:minute() == 1)
assert(d:second() == 0)

d = date.fromtime(d:time() + 12 * 60 * 60 * 1000)

assert(d)
assert(d:day() == 1)
assert(d:month() == 1)
assert(d:year() == 2020)
assert(d:hour() == 12)
assert(d:minute() == 1)
assert(d:second() == 0)

d = date.fromtime(d:time() + 5 * 24 * 60 * 60 * 1000)

assert(d)
assert(d:day() == 6)
assert(d:month() == 1)
assert(d:year() == 2020)
assert(d:hour() == 12)
assert(d:minute() == 1)
assert(d:second() == 0)

d, err = date.parse('')

assert(not d)
assert(err:find 'Unparseable date')

-- testing strings to dates and dates to strings

d, err = date.parse('2020-01-01 00:00:00')

assert(d)
assert(not err)
assert(d:day() == 1)
assert(d:month() == 1)
assert(d:year() == 2020)
assert(d:hour() == 0)
assert(d:minute() == 0)
assert(d:second() == 0)

assert(tostring(d) == '2020-01-01 00:00:00')
assert(d:format(dateFormat2) == '200101000000')
assert(d:format(dateFormat3) == '2020.01.01 AD at 00:00:00')

d, err = date.parse('2020-01-01 00:00:00', dateFormat)

assert(d)
assert(not err)
assert(d:day() == 1)
assert(d:month() == 1)
assert(d:year() == 2020)
assert(d:hour() == 0)
assert(d:minute() == 0)
assert(d:second() == 0)

assert(tostring(d) == '2020-01-01 00:00:00')
assert(d:format(dateFormat2) == '200101000000')
assert(d:format(dateFormat3) == '2020.01.01 AD at 00:00:00')

d, err = date.parse('2050-07-30 03:00:02')

local d2, d3 = d, d:clone()

assert(d == d2)
assert(d == d3)
assert(d2 == d3)
assert(rawequal(d, d2))
assert(rawequal(d, d3))
assert(rawequal(d2, d3))

assert(d)
assert(not err)
assert(d:day() == 30)
assert(d:month() == 7)
assert(d:year() == 2050)
assert(d:hour() == 3)
assert(d:minute() == 0)
assert(d:second() == 2)

assert(rawequal(d, d:year(2021)))

assert(d:day() == 30)
assert(d:month() == 7)
assert(d:year() == 2021)
assert(d:hour() == 3)
assert(d:minute() == 0)
assert(d:second() == 2)

assert(rawequal(d, d:minute(22)))

assert(d:day() == 30)
assert(d:month() == 7)
assert(d:year() == 2021)
assert(d:hour() == 3)
assert(d:minute() == 22)
assert(d:second() == 2)

assert(rawequal(d, d:day(d:day() - 8)))

assert(d:day() == 22)
assert(d:month() == 7)
assert(d:year() == 2021)
assert(d:hour() == 3)
assert(d:minute() == 22)
assert(d:second() == 2)

assert(rawequal(d, d:second(d:second() + 20)))

assert(d:day() == 22)
assert(d:month() == 7)
assert(d:year() == 2021)
assert(d:hour() == 3)
assert(d:minute() == 22)
assert(d:second() == 22)

assert(rawequal(d, d:hour(d:hour() + 19)))

assert(d:day() == 22)
assert(d:month() == 7)
assert(d:year() == 2021)
assert(d:hour() == 22)
assert(d:minute() == 22)
assert(d:second() == 22)

assert(rawequal(d, d:month(2)))

assert(d:day() == 22)
assert(d:month() == 2)
assert(d:year() == 2021)
assert(d:hour() == 22)
assert(d:minute() == 22)
assert(d:second() == 22)

assert(rawequal(d, d:year(2022)))

assert(d:day() == 22)
assert(d:month() == 2)
assert(d:year() == 2022)
assert(d:hour() == 22)
assert(d:minute() == 22)
assert(d:second() == 22)

assert(tostring(d) == '2022-02-22 22:22:22')
assert(d:format(dateFormat2) == '220222222222')
assert(d:format(dateFormat3) == '2022.02.22 AD at 22:22:22')
assert(tostring(d3) == '2050-07-30 03:00:02')
assert(d3:format(dateFormat2) == '500730030002')
assert(d3:format(dateFormat3) == '2050.07.30 AD at 03:00:02')
assert(d == d2)
assert(d ~= d3)
assert(not rawequal(d, d3))
assert(rawequal(d, d2))

d, err = date.parse('220222222222', dateFormat2)

assert(d)
assert(not err)
assert(d:day() == 22)
assert(d:month() == 2)
assert(d:year() == 2022)
assert(d:hour() == 22)
assert(d:minute() == 22)
assert(d:second() == 22)

assert(tostring(d) == '2022-02-22 22:22:22')
assert(d:format(dateFormat2) == '220222222222')
assert(d:format(dateFormat3) == '2022.02.22 AD at 22:22:22')

d, err = date.parse('2021.06.29 AD at 15:24:56', dateFormat3)

assert(d)
assert(not err)
assert(d:day() == 29)
assert(d:month() == 6)
assert(d:year() == 2021)
assert(d:hour() == 15)
assert(d:minute() == 24)
assert(d:second() == 56)

assert(tostring(d) == '2021-06-29 15:24:56')
assert(d:format(dateFormat2) == '210629152456')
assert(d:format(dateFormat3) == '2021.06.29 AD at 15:24:56')

d, err = date.parse '2022-10-30 00:00:00'

assert(d)
assert(not err)
assert(d:day() == 30)
assert(d:month() == 10)
assert(d:year() == 2022)
assert(d:hour() == 0)
assert(d:minute() == 0)
assert(d:second() == 0)

assert(tostring(d) == '2022-10-30 00:00:00')
assert(d:format(dateFormat2) == '221030000000')
assert(d:format(dateFormat3) == '2022.10.30 AD at 00:00:00')

return true