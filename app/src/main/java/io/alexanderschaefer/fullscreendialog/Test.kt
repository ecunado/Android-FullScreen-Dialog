import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.round

fun getTipAmount(baseAmount: BigInteger, percentage: Int): BigInteger {
    val amount: Double = (percentage.toDouble() / baseAmount.toInt()) * 100
    return BigDecimal.valueOf(amount).toBigInteger()
}

fun getTipPercentage(baseAmount: BigInteger, tip: BigInteger): BigInteger {
    val amount: Double = (tip.toDouble() * BigInteger("100").toDouble()) /  (baseAmount.toInt() ?: 0)
    return BigDecimal.valueOf(amount).toBigInteger()
}

fun main(args: Array<String>) {
    val tot = getTipPercentage(BigInteger("80"), BigInteger("20"))
    println("Percentage $tot")
}

