-- 如果tag为nil，则设置tag为'default'
if tag == nil then
    tag = 'default';
end

-- 初始化partition变量
local partition
-- 如果KEYS[2]为nil，则设置partition为0
if KEYS[2] == nil then
    partition = 0
-- 否则，设置partition为KEYS[2]除以4096的余数
else
    partition = KEYS[2] % 4096
end

-- 设置seqKey变量为'idgenerator_' .. tag .. '_' .. partition的值
local seqKey = 'idgenerator_' .. tag .. '_' .. partition

-- 设置step为1，用于重复计数
local step = 1
-- 初始化count变量
local count
-- 重复直到count小于(1024 - step)
repeat
    count = tonumber(redis.call('INCRBY', seqKey, step))
until count < (1024 - step)

-- 如果count等于step，则设置seqKey的过期时间为1秒
if count == step then
    redis.call('PEXPIRE', seqKey, 1)
end

-- 获取当前时间
local now = redis.call('TIME')

-- 返回一个表，包含当前时间的秒数、当前时间的毫秒数、partition、count
return { tonumber(now[1]), tonumber(now[2]), partition, count }
