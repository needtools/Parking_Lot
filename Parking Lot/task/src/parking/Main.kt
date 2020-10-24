package parking

import java.util.*
class Park{
    var spotNumber=0
    var occupied = false
    var carColor=""
    var carNumber=""
    companion object Phrases {
        const val full = "Sorry, the parking lot is full."
        const val parked =" car parked in spot "
        const val dot ="."
        const val spot = "Spot "
        const val free =  " is free."
        const val noCreated = "Sorry, a parking lot has not been created."
        const val created ="Created a parking lot with "
        const val spots = " spots."
        const val empty = "Parking lot is empty."
        const val noColor = "No cars with color "
        const val found = " were found."
        const val noReg = "No cars with registration number "
    }
}

fun main() {

    val scanner = Scanner(System.`in`)
    var spotList = createSpots(0)

        var input: List<String>
        w@ while (scanner.hasNext() && spotList.size>0) {
            input = scanner.nextLine().split(" ")
            when {
                input[0] == "park" -> {
                    b@ for (i in spotList) {
                        if (!i.occupied) {
                            i.occupied = true
                            i.carNumber = input[1]
                            i.carColor = input[2]
                            println(i.carColor + Park.parked + i.spotNumber + Park.dot)
                            break@b
                        }
                        if (i.spotNumber == spotList.size && i.occupied) {
                            println(Park.full)
                            break@b
                        }
                    }
                }
                input[0] == "leave" -> {
                    f@ for (i in spotList) {
                        if (i.spotNumber == input[1].toInt()) {
                            i.occupied = false
                            i.carNumber = ""
                            i.carColor = ""
                            println(Park.spot + i.spotNumber + Park.free)
                            break@f
                        }
                    }
                }

                input[0] == "status" -> {
                    var count = 0
                    for (i in spotList) {
                        if (i.occupied) {
                            println("${i.spotNumber} ${i.carNumber} ${i.carColor}")
                            count++
                        }
                    }
                    if (count == 0) println(Park.empty)
                }

                input[0] == "reg_by_color" -> {
                    var count = 0
                    var regByColor=""
                    for (i in spotList) {
                        if (i.occupied) {
                            count++
                        }
                        if (i.carColor.toUpperCase()==input[1].toUpperCase()) {
                            regByColor+=i.carNumber+", "
                        }
                    }
                    if (count == 0) println(Park.empty)

                    if (regByColor!="") println(regByColor.dropLast(2))
                    else println(Park.noColor+input[1]+Park.found)
                }

                input[0] == "spot_by_color" -> {
                    var count = 0
                    var spotsByColor=""
                    for (i in spotList) {
                        if (i.occupied) {
                            count++
                        }
                        if (i.carColor.toUpperCase()==input[1].toUpperCase()) {
                            spotsByColor+=i.spotNumber.toString()+", "
                        }
                    }
                    if (count == 0) println(Park.empty)

                    if (spotsByColor!="") println(spotsByColor.dropLast(2))
                    else println(Park.noColor+input[1]+Park.found)

                }

                input[0] == "spot_by_reg" -> {
                    var count = 0
                    for (i in spotList) {
                        if (i.occupied) {
                            count++
                        }
                        if (i.carNumber==input[1]) {
                            println(i.spotNumber)
                            continue@w
                        }
                    }
                    if (count == 0) println(Park.empty)
                    else println(Park.noReg+input[1]+Park.found)

                }

                input[0] == "create" -> {// spots were created but now recreates.
                    spotList.clear()
                    spotList = createSpots(input[1].toInt())
                    continue@w
                }

                input[0] == "exit" -> {
                    break@w
                }
            }
    }
}
// ++++++++++++++++++++++++++++++++++
fun createSpots(s: Int): MutableList<Park> {
    var spots = -1// no created spots
    val spotList = mutableListOf<Park>()// 0..spotNumber-1
    if (s == 0) {// init
    c@ while(spots == -1) {
            val createScanner = Scanner(System.`in`)
            val cr = createScanner.nextLine().split(" ")
            if (cr[0] == "create") {
                spots = cr[1].toInt()// number of created spots
                break@c
            }
            if (cr[0] == "park" || cr[0] == "leave" || cr[0] == "status" || cr[0] == "reg_by_color" ||
                    cr[0] == "spot_by_color" || cr[0] == "spot_by_reg"   ) {
                println(Park.noCreated)
                continue@c
            }
            if (cr[0] == "exit") {
                break@c
            }
        }

        if (spots > 0) {// spots were created but now recreates. Before this  spotList.clear()
            for (i in 1..spots) {
                val park = Park()
                park.spotNumber = i
                spotList.add(park)
            }
            println(Park.created + spots + Park.spots)
        }

    }
    if(s>0){// after init
        spots=s
        println(Park.created + spots + Park.spots)
        for (i in 1..spots) {
            val park = Park()
            park.spotNumber = i
            spotList.add(park)
        }
    }
    return spotList
}

