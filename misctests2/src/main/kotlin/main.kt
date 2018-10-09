import com.github.kittinunf.fuel.Fuel
import org.jetbrains.exposed.sql.transactions.transaction
import Song.SongArrayDeserializer
import org.jetbrains.exposed.sql.*

fun main(args: Array<String>) {
    val url = "https://next.json-generator.com/api/json/get/EkeSgmXycS"

    val (request, response, result) = Fuel.get(url).responseObject(Song.SongArrayDeserializer())
    val (songs, err) = result
    Thread.sleep(5000)

    Database.connect(   //Conexion with data base
            "jdbc:postgresql:Musica",
            "org.postgresql.Driver",
            "postgres",
            "12345678"
    )

    transaction {//Creation of table
        SchemaUtils.create(Album)
        songs?.forEach { Song -> run{
                Album.insert {//Inserting each song
                    it[year] = Song.year
                    it[country] = Song.country
                    it[region] = Song.region
                    it[artistName] = Song.artistName
                    it[song] = Song.song
                    it[artistGender] = Song.artistGender
                    it[groupOrSolo] = Song.groupOrSolo
                    it[place] = Song.place
                    it[points] = Song.points
                    it[isFinal] = Song.isFinal
                    it[isSongInEnglish] = Song.isSongInEnglish
                    it[songQuality] = Song.songQuality
                    it[normalizedPoints] = Song.normalizedPoints
                    it[energy] = Song.energy
                    it[duration] = Song.duration
                    it[acousticness] = Song.acousticness
                    it[danceability] = Song.danceability
                    it[tempo] = Song.tempo
                    it[speechiness] = Song.speechiness
                    it[key] = Song.key
                    it[liveness] = Song.liveness
                    it[timeSignature] = Song.timeSignature
                    it[mode] = Song.mode
                    it[loudness] = Song.loudness
                    it[valence] = Song.valence
                    it[happiness] = Song.happiness
                    it[favourite] = Song.favourite
                }
            }
        }
    }

var wantsToContinue = true
    do {
        var menu = "Menu Principal:\n" +
            "-------------------------\n" +
            "1.Buscar canciones por nombre\n" +
            "2.Buscar canciones por artista\n" +
            "3.Mostrar todas mis canciones favoritas\n"+
            "4.Salir"
        println(menu)
        print("Ingrese una opcion: ")
        val strOption = readLine()!!
        val option = strOption.toInt()
        when (option) {
            1 -> { //Buscar por nombre
                print("Ingrese parte del nombre de la cancion que desea buscar: ")
                val selectedSong = readLine()!!.toString()
                transaction {
                    (Album).slice(Album.song).select{ Album.song.like("%$selectedSong%" )}.forEach {
                        println(it.data[4])
                    }
                }
                println("Desea guardar alguna cancion como favorita?")
                var wantsToSave = readLine().toString()
                if (wantsToSave.toUpperCase() == "SI"){
                    println("Cual?")
                    var optionSelected= readLine()!!.toInt()
                    var indexOfSong = optionSelected - 1
                    transaction {
                        Album.select{ Album.song.like("%$selectedSong%" )}.elementAt(indexOfSong)
                    }
                    println("Ok, listo...")
                }
            }
            2 -> { //Buscar por artista
                print("Ingrese parte del nombre del artista que desea buscar: ")
                val selectedArtist = readLine()!!.toString()
                transaction {
                    (Album).slice(Album.song).select{ Album.artistName.like("%$selectedArtist%" )}.forEach {
                        println(it.data[3])
                     }
                }
                println("Desea guardar alguna cancion como favorita?")
                var wantsToSave = readLine().toString()
                if (wantsToSave.toUpperCase() == "SI"){
                    println("Cual?")
                    var optionSelected= readLine()!!.toInt()
                    var indexOfSong = optionSelected - 1
                    transaction {
                        Album.select{ Album.artistName.like("%$selectedArtist%" )}.elementAt(indexOfSong)
                    }
                    println("Ok, listo...")
                }
            }
            3 -> { //Imprimir favoritas
                transaction {
                    (Album).slice(Album.song).select{ Album.favourite.eq(true)}.forEach{
                        println(it.data[4])
                    }
                }
            }
            4 -> { //Cerrar ciclo
                wantsToContinue = !wantsToContinue
            }
            else -> { //Si no ingresa una opcion valida
                println("Opcion no valida...")
            }
        }
    } while (wantsToContinue)
}