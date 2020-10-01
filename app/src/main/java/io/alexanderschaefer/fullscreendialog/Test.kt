import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.round

fun getTipAmount(baseAmount: BigInteger, percentage: Int): BigInteger {
    val amount: Double = (percentage.toDouble() / baseAmount.toInt()) * 100
    return BigDecimal.valueOf(amount).toBigInteger()
}

fun main(args: Array<String>) {
    val tot = getTipAmount(BigInteger("80"), 12)
    println("Amount $tot")
}

