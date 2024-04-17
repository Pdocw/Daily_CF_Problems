fun inv(a: Long, p: Long, invPModA: Long): Long {
    val ans = -p / a * invPModA % p
    return if (ans < 0) ans + p else ans
}
fun inv(a: Int, p: Int, invPModA: Int) = inv(a.toLong(), p.toLong(), invPModA.toLong()).toInt()
inline fun iar(size: Int, init: (Int) -> Int = { 0 }) = IntArray(size) { init(it) }
open class Comb(val cap: Int, val mod: Int) {
    val fac = IntArray(cap + 1)
    val inv = IntArray(cap + 1)
    val facInv = IntArray(cap + 1)
    init {
        fac[0] = 1; facInv[0] = 1
        for (i in 1..cap) {
            inv[i] = if (i == 1) 1 else inv(i, mod, inv[mod % i])
            fac[i] = (fac[i - 1].toLong() * i % mod).toInt()
            facInv[i] = (facInv[i - 1].toLong() * inv[i] % mod).toInt()
        }
    }
    fun c(n: Int, m: Int): Int {
        return if (n == m) 1 else if (n !in 0..cap || m !in 0..n) 0 else {
            // C(n, m) = n! / m! / (n - m)!
            (fac[n].toLong() * facInv[m] % mod * facInv[n - m] % mod).toInt()
        }
    }
    fun a(n: Int, m: Int): Int {
        return if (n !in 0..cap || m !in 0..n) 0 else {
            // A(n, m) = n! / (n - m)!
            (fac[n].toLong() * facInv[n - m] % mod).toInt()
        }
    }
}

/**
 * generated by kotlincputil@tauros
 */
fun main(args: Array<String>) {
    // https://codeforces.com/problemset/problem/1526/E
    // https://github.com/Yawn-Sean/Daily_CF_Problems/blob/main/daily_problems/2024/03/0323/solution/cf1526e.md
    // 只想到要排出每个字符的大小关系，但是不会排，也不会处理排出来后的等号
    val (n, k) = readln().split(" ").map { it.toInt() }
    val sa = readln().split(" ").map { it.toInt() }.toIntArray()

    val rk = iar(n)
    for (i in 0 until n) rk[sa[i]] = i
    var eqCnt = 0
    for (i in 0 until n - 1) {
        val (cur, nex) = sa[i] to sa[i + 1]
        if (cur + 1 >= n || nex + 1 < n && rk[cur + 1] < rk[nex + 1]) {
            eqCnt += 1
        }
    }
    // 答案就是C(k+eqCnt, n)
    val comb = Comb(k + eqCnt, 998244353)
    val ans = comb.c(k + eqCnt, n)
    println(ans)
}