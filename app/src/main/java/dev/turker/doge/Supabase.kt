package dev.turker.doge

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

val supabaseClient = createSupabaseClient(
    supabaseUrl = "https://xxctovzmgkwnmxgbzysp.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inh4Y3RvdnptZ2t3bm14Z2J6eXNwIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTgzMjYwNTcsImV4cCI6MjAxMzkwMjA1N30.RXtFL1vfur2QPGA7VI9f1KEOwoxCvFE2XD2kDiBYFrs"
){
    this.install(GoTrue)
    this.install(Postgrest)
}