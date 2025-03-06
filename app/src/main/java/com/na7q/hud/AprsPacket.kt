package com.na7q.hud

data class AprsPacket(
    val comment: String
) {
    companion object {
	
		private val KENWOOD_COMMENT_DATA = mapOf(
					">" to mapOf("vendor" to "Kenwood", "model" to "TH-D7A", "class" to "ht"),
					"=" to mapOf("vendor" to "Kenwood", "model" to "TH-D72", "class" to "ht"),
					"^" to mapOf("vendor" to "Kenwood", "model" to "TH-D74", "class" to "ht"),
					"&" to mapOf("vendor" to "Kenwood", "model" to "TH-D75", "class" to "ht"),
					"=" to mapOf("vendor" to "Kenwood", "model" to "TM-D710", "class" to "rig"),
					"]" to mapOf("vendor" to "Kenwood", "model" to "TM-D700", "class" to "rig")					
		)	
		
		private val COMMENT_DATA = mapOf(
					"_ " to mapOf("vendor" to "Yaesu", "model" to "VX-8", "class" to "ht"),
					"_\"" to mapOf("vendor" to "Yaesu", "model" to "FTM-350", "class" to "rig"),
					"_#" to mapOf("vendor" to "Yaesu", "model" to "VX-8G", "class" to "ht"),
					"_$" to mapOf("vendor" to "Yaesu", "model" to "FT1D", "class" to "ht"),
					"_(" to mapOf("vendor" to "Yaesu", "model" to "FT2D", "class" to "ht"),
					"_0" to mapOf("vendor" to "Yaesu", "model" to "FT3D", "class" to "ht"),
					"_3" to mapOf("vendor" to "Yaesu", "model" to "FT5D", "class" to "ht"),
					"_1" to mapOf("vendor" to "Yaesu", "model" to "FTM-300D", "class" to "rig"),
					"_2" to mapOf("vendor" to "Yaesu", "model" to "FTM-200D", "class" to "rig"),
					"_4" to mapOf("vendor" to "Yaesu", "model" to "FTM-500D", "class" to "rig"),
					"_)" to mapOf("vendor" to "Yaesu", "model" to "FTM-100D", "class" to "rig"),
					"_%" to mapOf("vendor" to "Yaesu", "model" to "FTM-400DR", "class" to "rig"),
					"(5" to mapOf("vendor" to "Anytone", "model" to "D578UV", "class" to "ht"),
					"(8" to mapOf("vendor" to "Anytone", "model" to "D878UV", "class" to "ht"),
					"|3" to mapOf("vendor" to "Byonics", "model" to "TinyTrak3", "class" to "tracker"),
					"|4" to mapOf("vendor" to "Byonics", "model" to "TinyTrak4", "class" to "tracker"),
					"^v" to mapOf("vendor" to "HinzTec", "model" to "anyfrog"),
					"*v" to mapOf("vendor" to "KissOZ", "model" to "Tracker", "class" to "tracker"),
					":2" to mapOf("vendor" to "SQ8L", "model" to "VP-Tracker", "class" to "tracker"),
					" X" to mapOf("vendor" to "SainSonic", "model" to "AP510", "class" to "tracker"),
					"[1" to mapOf("vendor" to "NA7Q", "model" to "APRSdroid", "class" to "software")
		)

		private val TOCALL_DATA = mapOf(
					"AP1WWX" to mapOf("vendor" to "TAPR", "model" to "T-238+", "class" to "wx"),
					"AP4R??" to mapOf("vendor" to "Open Source", "model" to "APRS4R", "class" to "software"),
					"APAEP1" to mapOf("vendor" to "Paraguay Space Agency (AEP)", "model" to "EIRUAPRSDIGIS&FV1", "class" to "satellite"),
					"APAF??" to mapOf("vendor" to "", "model" to "AFilter"),
					"APAG??" to mapOf("vendor" to "", "model" to "AGate"),
					"APAGW" to mapOf("vendor" to "SV2AGW", "model" to "AGWtracker", "class" to "software"),
					"APAGW?" to mapOf("vendor" to "SV2AGW", "model" to "AGWtracker", "class" to "software"),
					"APAH??" to mapOf("vendor" to "", "model" to "AHub"),
					"APAIOR" to mapOf("vendor" to "J. Angelo Racoma DU2XXR/N2RAC", "model" to "APRSPH net bot based on Ioreth", "class" to "service"),
					"APAM??" to mapOf("vendor" to "Altus Metrum", "model" to "AltOS", "class" to "tracker"),
					"APAND?" to mapOf("vendor" to "Open Source", "model" to "APRSdroid", "class" to "app"),
					"APAR??" to mapOf("vendor" to "Øyvind, LA7ECA", "model" to "Arctic Tracker", "class" to "tracker"),
					"APAT51" to mapOf("vendor" to "Anytone", "model" to "AT-D578", "class" to "rig"),
					"APAT81" to mapOf("vendor" to "Anytone", "model" to "AT-D878", "class" to "ht"),
					"APAT??" to mapOf("vendor" to "Anytone", "model" to ""),
					"APATAR" to mapOf("vendor" to "TA7W/OH2UDS Baris Dinc, TA6AD Emre Keles", "model" to "ATA-R APRS Digipeater", "class" to "digi"),
					"APAVT5" to mapOf("vendor" to "SainSonic", "model" to "AP510", "class" to "tracker"),
					"APAW??" to mapOf("vendor" to "SV2AGW", "model" to "AGWPE", "class" to "software"),
					"APAX??" to mapOf("vendor" to "", "model" to "AFilterX"),
					"APB2MF" to mapOf("vendor" to "Mike, DL2MF", "model" to "MF2APRS Radiosonde tracking tool", "class" to "software"),
					"APBK??" to mapOf("vendor" to "PY5BK", "model" to "Bravo Tracker", "class" to "tracker"),
					"APBL??" to mapOf("vendor" to "BigRedBee", "model" to "BeeLine GPS", "class" to "tracker"),
					"APBM??" to mapOf("vendor" to "R3ABM", "model" to "BrandMeister DMR"),
					"APBPQ?" to mapOf("vendor" to "John Wiseman, G8BPQ", "model" to "BPQ32", "class" to "software"),
					"APBSD?" to mapOf("vendor" to "hambsd.org", "model" to "HamBSD"),
					"APBT*" to mapOf("vendor" to "BTECH", "model" to ""),
					"APBT62" to mapOf("vendor" to "BTECH", "model" to "DMR 6x2", "class" to "ht"),
					"APBTUV" to mapOf("vendor" to "BTECH", "model" to "UV-PRO", "class" to "ht"),
					"APC???" to mapOf("vendor" to "Rob Wittner, KZ5RW", "model" to "APRS/CE", "class" to "app"),
					"APCDS0" to mapOf("vendor" to "ZS6LMG", "model" to "cell tracker", "class" to "tracker"),
					"APCLEY" to mapOf("vendor" to "ZS6EY", "model" to "EYTraker", "class" to "tracker"),
					"APCLEZ" to mapOf("vendor" to "ZS6EY", "model" to "Telit EZ10 GSM application", "class" to "tracker"),
					"APCLUB" to mapOf("vendor" to "", "model" to "Brazil APRS network"),
					"APCLWX" to mapOf("vendor" to "ZS6EY", "model" to "EYWeather", "class" to "wx"),
					"APCN??" to mapOf("vendor" to "DG5OAW", "model" to "carNET"),
					"APCSMS" to mapOf("vendor" to "USNA", "model" to "Cosmos"),
					"APCSS" to mapOf("vendor" to "AMSAT", "model" to "CubeSatSim CubeSat Simulator"),
					"APCTLK" to mapOf("vendor" to "Open Source", "model" to "Codec2Talkie", "class" to "app"),
					"APCWP8" to mapOf("vendor" to "GM7HHB", "model" to "WinphoneAPRS", "class" to "app"),
					"APD5T?" to mapOf("vendor" to "Geoffrey, F4FXL", "model" to "Open Source DStarGateway", "class" to "dstar"),
					"APDF??" to mapOf("vendor" to "", "model" to "Automatic DF units"),
					"APDG??" to mapOf("vendor" to "Jonathan, G4KLX", "model" to "ircDDB Gateway", "class" to "dstar"),
					"APDI??" to mapOf("vendor" to "Bela, HA5DI", "model" to "DIXPRS", "class" to "software"),
					"APDNO?" to mapOf("vendor" to "DO3SWW", "model" to "APRSduino", "class" to "tracker"),
					"APDPRS" to mapOf("vendor" to "unknown", "model" to "D-Star APDPRS", "class" to "dstar"),
					"APDR??" to mapOf("vendor" to "Open Source", "model" to "APRSdroid", "class" to "app"),
					"APDS??" to mapOf("vendor" to "SP9UOB", "model" to "dsDIGI"),
					"APDST?" to mapOf("vendor" to "SP9UOB", "model" to "dsTracker"),
					"APDT??" to mapOf("vendor" to "unknown", "model" to "APRStouch Tone (DTMF)"),
					"APDU??" to mapOf("vendor" to "JA7UDE", "model" to "U2APRS", "class" to "app"),
					"APDV??" to mapOf("vendor" to "OE6PLD", "model" to "SSTV with APRS", "class" to "software"),
					"APDW??" to mapOf("vendor" to "WB2OSZ", "model" to "DireWolf"),
					"APD???" to mapOf("vendor" to "Open Source", "model" to "aprsd", "class" to "software"),
					"APE2A?" to mapOf("vendor" to "NoseyNick, VA3NNW", "model" to "Email-2-APRS gateway", "class" to "software"),
					"APE???" to mapOf("vendor" to "", "model" to "Telemetry devices"),
					"APECAN" to mapOf("vendor" to "KT5TK/DL7AD", "model" to "Pecan Pico APRS Balloon Tracker", "class" to "tracker"),
					"APELK?" to mapOf("vendor" to "WB8ELK", "model" to "Balloon tracker", "class" to "tracker"),
					"APEML?" to mapOf("vendor" to "Leszek, SP9MLI", "model" to "SP9MLI for WX, Telemetry", "class" to "software"),
					"APEP??" to mapOf("vendor" to "Patrick EGLOFF, TK5EP", "model" to "LoRa WX station", "class" to "wx"),
					"APERRB" to mapOf("vendor" to "KG5JNC", "model" to "APRS Backend for Errbot", "class" to "service"),
					"APERS?" to mapOf("vendor" to "Jason, KG7YKZ", "model" to "Runner tracking", "class" to "tracker"),
					"APERXQ" to mapOf("vendor" to "PE1RXQ", "model" to "PE1RXQ APRS Tracker", "class" to "tracker"),
					"APESP1" to mapOf("vendor" to "LY3PH", "model" to "APRS-ESP"),
					"APESPG" to mapOf("vendor" to "OH2TH", "model" to "ESP SmartBeacon APRS-IS Client"),
					"APESPW" to mapOf("vendor" to "OH2TH", "model" to "ESP Weather Station APRS-IS Client"),
					"APETBT" to mapOf("vendor" to "PD7R", "model" to "TBTracker Balloon Telemetry Tracker", "class" to "tracker"),
					"APFG??" to mapOf("vendor" to "KP4DJT", "model" to "Flood Gage", "class" to "software"),
					"APFI??" to mapOf("vendor" to "aprs.fi", "model" to "", "class" to "app"),
					"APFII?" to mapOf("vendor" to "aprs.fi", "model" to "iPhone/iPad app", "class" to "app"),
					"APGBLN" to mapOf("vendor" to "NW5W", "model" to "GoBalloon", "class" to "tracker"),
					"APGDT?" to mapOf("vendor" to "VK4FAST", "model" to "Graphic Data Terminal"),
					"APGO??" to mapOf("vendor" to "AA3NJ", "model" to "APRS-Go", "class" to "app"),
					"APHAX?" to mapOf("vendor" to "PY2UEP", "model" to "SM2APRS SondeMonitor", "class" to "software"),
					"APHBL?" to mapOf("vendor" to "KF7EEL", "model" to "HBLink D-APRS Gateway", "class" to "software"),
					"APHH?" to mapOf("vendor" to "Steven D. Bragg, KA9MVA", "model" to "HamHud", "class" to "tracker"),
					"APHK??" to mapOf("vendor" to "LA1BR", "model" to "Digipeater/tracker"),
					"APHMEY" to mapOf("vendor" to "Tapio Heiskanen, OH2TH", "model" to "APRS-IS Client for Athom Homey"),
					"APHPIA" to mapOf("vendor" to "HP3ICC", "model" to "Arduino APRS"),
					"APHPIB" to mapOf("vendor" to "HP3ICC", "model" to "Python APRS Beacon"),
					"APHPIW" to mapOf("vendor" to "HP3ICC", "model" to "Python APRS WX"),
					"APHRM?" to mapOf("vendor" to "Giovanni, IW1CGW", "model" to "Meteo", "class" to "wx"),
					"APHRT?" to mapOf("vendor" to "Giovanni, IW1CGW", "model" to "Telemetry"),
					"APHT??" to mapOf("vendor" to "IU0AAC", "model" to "HMTracker", "class" to "tracker"),
					"APHW??" to mapOf("vendor" to "HamWAN", "model" to ""),
					"API282" to mapOf("vendor" to "Icom", "model" to "IC-2820", "class" to "dstar"),
					"API31" to mapOf("vendor" to "Icom", "model" to "IC-31", "class" to "dstar"),
					"API410" to mapOf("vendor" to "Icom", "model" to "IC-4100", "class" to "dstar"),
					"API51" to mapOf("vendor" to "Icom", "model" to "IC-51", "class" to "dstar"),
					"API510" to mapOf("vendor" to "Icom", "model" to "IC-5100", "class" to "dstar"),
					"API710" to mapOf("vendor" to "Icom", "model" to "IC-7100", "class" to "dstar"),
					"API80" to mapOf("vendor" to "Icom", "model" to "IC-80", "class" to "dstar"),
					"API880" to mapOf("vendor" to "Icom", "model" to "IC-880", "class" to "dstar"),
					"API910" to mapOf("vendor" to "Icom", "model" to "IC-9100", "class" to "dstar"),
					"API92" to mapOf("vendor" to "Icom", "model" to "IC-92", "class" to "dstar"),
					"API970" to mapOf("vendor" to "Icom", "model" to "IC-9700", "class" to "dstar"),
					"API???" to mapOf("vendor" to "Icom", "model" to "unknown", "class" to "dstar"),
					"APIC??" to mapOf("vendor" to "HA9MCQ", "model" to "PICiGATE"),
					"APIE??" to mapOf("vendor" to "W7KMV", "model" to "PiAPRS"),
					"APIN??" to mapOf("vendor" to "AB0WV", "model" to "PinPoint"),
					"APIZCI" to mapOf("vendor" to "TA7W/OH2UDS Baris Dinc, TA6AD Emre Keles", "model" to "hymTR IZCI Tracker", "class" to "tracker"),
					"APJ8??" to mapOf("vendor" to "KN4CRD", "model" to "JS8Call", "class" to "software"),
					"APJA??" to mapOf("vendor" to "K4HG & AE5PL", "model" to "JavAPRS"),
					"APJE??" to mapOf("vendor" to "Gregg Wonderly, W5GGW", "model" to "JeAPRS"),
					"APJI??" to mapOf("vendor" to "Peter Loveall, AE5PL", "model" to "jAPRSIgate", "class" to "software"),
					"APJID2" to mapOf("vendor" to "Peter Loveall, AE5PL", "model" to "D-Star APJID2", "class" to "dstar"),
					"APJS??" to mapOf("vendor" to "Peter Loveall, AE5PL", "model" to "javAPRSSrvr"),
					"APJY??" to mapOf("vendor" to "KA2DDO", "model" to "YAAC", "class" to "software"),
					"APK003" to mapOf("vendor" to "Kenwood", "model" to "TH-D72", "class" to "ht"),
					"APK004" to mapOf("vendor" to "Kenwood", "model" to "TH-D74", "class" to "ht"),
					"APK005" to mapOf("vendor" to "Kenwood", "model" to "TH-D75", "class" to "ht"),
					"APK0??" to mapOf("vendor" to "Kenwood", "model" to "TH-D7", "class" to "ht"),
					"APK1??" to mapOf("vendor" to "Kenwood", "model" to "TM-D700", "class" to "rig"),
					"APKDXn" to mapOf("vendor" to "KelateDX, 9M2D", "model" to "LAHKHUANO APRS", "class" to "tracker"),
					"APKEYn" to mapOf("vendor" to "9W2KEY", "model" to "ATMega328P APRS", "class" to "tracker"),
					"APKHTW" to mapOf("vendor" to "Kip, W3SN", "model" to "Tempest Weather Bridge", "class" to "wx"),
					"APKRAM" to mapOf("vendor" to "kramstuff.com", "model" to "Ham Tracker", "class" to "app"),
					"APLC??" to mapOf("vendor" to "DL3DCW", "model" to "APRScube"),
					"APLDAG" to mapOf("vendor" to "Inigo, EA2CQ", "model" to "DAGA LoRa/APRS SOTA spotting", "class" to "service"),
					"APLDG?" to mapOf("vendor" to "Eddie, 9W2LWK", "model" to "LoRAIGate", "class" to "igate"),
					"APLDH?" to mapOf("vendor" to "Eddie, 9W2LWK", "model" to "LoraTracker", "class" to "tracker"),
					"APLDI?" to mapOf("vendor" to "David, OK2DDS", "model" to "LoRa IGate/Digipeater", "class" to "digi"),
					"APLDM?" to mapOf("vendor" to "David, OK2DDS", "model" to "LoRa Meteostation", "class" to "wx"),
					"APLETK" to mapOf("vendor" to "DL5TKL", "model" to "T-Echo", "class" to "tracker"),
					"APLFG?" to mapOf("vendor" to "Gabor, HG3FUG", "model" to "LoRa WX station", "class" to "wx"),
					"APLFLY" to mapOf("vendor" to "Damian, SQ2CPA", "model" to "ESP32 LoRa Balloon", "class" to "tracker"),
					"APLFM?" to mapOf("vendor" to "DO1MA", "model" to "FemtoAPRS", "class" to "tracker"),
					"APLG??" to mapOf("vendor" to "OE5BPA", "model" to "LoRa Gateway/Digipeater", "class" to "digi"),
					"APLHB9" to mapOf("vendor" to "SWISS-ARTG", "model" to "LoRa iGate RPI", "class" to "igate"),
					"APLHI?" to mapOf("vendor" to "Giovanni, IW1CGW", "model" to "LoRa IGate/Digipeater/Telemetry", "class" to "digi"),
					"APLHM?" to mapOf("vendor" to "Giovanni, IW1CGW", "model" to "LoRa Meteostation", "class" to "wx"),
					"APLIF?" to mapOf("vendor" to "TA5Y", "model" to "TIF LORA APRS I-GATE", "class" to "igate"),
					"APLIG?" to mapOf("vendor" to "TA2MUN/TA9OHC", "model" to "LightAPRS Tracker", "class" to "tracker"),
					"APLLO?" to mapOf("vendor" to "HB4LO", "model" to "HAB BOT", "class" to "tracker"),
					"APLM??" to mapOf("vendor" to "WA0TQG", "model" to "", "class" to "software"),
					"APLO??" to mapOf("vendor" to "SQ9MDD", "model" to "LoRa KISS TNC/Tracker", "class" to "tracker"),
					"APLP0?" to mapOf("vendor" to "SQ9P", "model" to "fajne digi", "class" to "digi"),
					"APLP1?" to mapOf("vendor" to "SQ9P", "model" to "LORA/FSK/AFSK fajny tracker", "class" to "tracker"),
					"APLPS?" to mapOf("vendor" to "Jose, XE3JAC", "model" to "ESP-32 LoRa"),
					"APLRG?" to mapOf("vendor" to "Ricardo, CA2RXU", "model" to "ESP32 LoRa iGate", "class" to "igate"),
					"APLRT?" to mapOf("vendor" to "Ricardo, CA2RXU", "model" to "ESP32 LoRa Tracker", "class" to "tracker"),
					"APLS??" to mapOf("vendor" to "SARIMESH", "model" to "SARIMESH", "class" to "software"),
					"APLT??" to mapOf("vendor" to "OE5BPA", "model" to "LoRa Tracker", "class" to "tracker"),
					"APLU0?" to mapOf("vendor" to "SP9UP", "model" to "ESP32/SX12xx LoRa iGate / Digi", "class" to "digi"),
					"APLU1?" to mapOf("vendor" to "SP9UP", "model" to "ESP32/SX12xx LoRa Tracker", "class" to "tracker"),
					"APLZA?" to mapOf("vendor" to "Huang Xuewu, BD5HTY", "model" to "LoRa"),
					"APLZX?" to mapOf("vendor" to "N1AF", "model" to "LoRa-APRS"),
					"APMAIL" to mapOf("vendor" to "Mike, NA7Q", "model" to "APRS Mailbox", "class" to "service"),
					"APMG??" to mapOf("vendor" to "Alex, AB0TJ", "model" to "PiCrumbs and MiniGate", "class" to "software"),
					"APMI01" to mapOf("vendor" to "Microsat", "model" to "WX3in1"),
					"APMI02" to mapOf("vendor" to "Microsat", "model" to "WXEth"),
					"APMI03" to mapOf("vendor" to "Microsat", "model" to "PLXDigi"),
					"APMI04" to mapOf("vendor" to "Microsat", "model" to "WX3in1 Mini"),
					"APMI05" to mapOf("vendor" to "Microsat", "model" to "PLXTracker"),
					"APMI06" to mapOf("vendor" to "Microsat", "model" to "WX3in1 Plus 2.0"),
					"APMI??" to mapOf("vendor" to "Microsat", "model" to ""),
					"APMON?" to mapOf("vendor" to "Amon Schumann, DL9AS", "model" to "APRS Balloon Tracker", "class" to "tracker"),
					"APMPAD" to mapOf("vendor" to "DF1JSL", "model" to "Multi-Purpose APRS Daemon", "class" to "service"),
					"APMQ??" to mapOf("vendor" to "WB2OSZ", "model" to "Ham Radio of Things"),
					"APMT??" to mapOf("vendor" to "LZ1PPL", "model" to "Micro APRS Tracker", "class" to "tracker"),
					"APN102" to mapOf("vendor" to "Gregg Wonderly, W5GGW", "model" to "APRSNow", "class" to "app"),
					"APN2??" to mapOf("vendor" to "VE4KLM", "model" to "NOSaprs for JNOS 2.0"),
					"APN3??" to mapOf("vendor" to "Kantronics", "model" to "KPC-3"),
					"APN9??" to mapOf("vendor" to "Kantronics", "model" to "KPC-9612"),
					"APNCM" to mapOf("vendor" to "Keith Kaiser, WA0TJT", "model" to "Net Control Manager", "class" to "software"),
					"APND??" to mapOf("vendor" to "PE1MEW", "model" to "DIGI_NED"),
					"APNIC4" to mapOf("vendor" to "SQ5EKU", "model" to "BidaTrak", "class" to "tracker"),
					"APNJS?" to mapOf("vendor" to "Julien Sansonnens, HB9HRD", "model" to "Web messaging service", "class" to "service"),
					"APNK01" to mapOf("vendor" to "Kenwood", "model" to "TM-D700", "class" to "rig"),
					"APNK80" to mapOf("vendor" to "Kantronics", "model" to "KAM"),
					"APNKMP" to mapOf("vendor" to "Kantronics", "model" to "KAM+"),
					"APNKMX" to mapOf("vendor" to "Kantronics", "model" to "KAM-XL"),
					"APNL??" to mapOf("vendor" to "OE5DXL, OE5HPM", "model" to "dxlAPRS", "class" to "daemon"),
					"APNM??" to mapOf("vendor" to "MFJ", "model" to "TNC"),
					"APNP??" to mapOf("vendor" to "PacComm", "model" to "TNC"),
					"APNT??" to mapOf("vendor" to "SV2AGW", "model" to "TNT TNC as a digipeater", "class" to "digi"),
					"APNU??" to mapOf("vendor" to "IW3FQG", "model" to "UIdigi", "class" to "digi"),
					"APNV0?" to mapOf("vendor" to "SQ8L", "model" to "VP-Digi", "class" to "digi"),
					"APNV1?" to mapOf("vendor" to "SQ8L", "model" to "VP-Node"),
					"APNV2?" to mapOf("vendor" to "SQ8L", "model" to "VP-Tracker", "class" to "tracker"),
					"APNV??" to mapOf("vendor" to "SQ8L", "model" to ""),
					"APNW??" to mapOf("vendor" to "SQ3FYK", "model" to "WX3in1"),
					"APNX??" to mapOf("vendor" to "K6DBG", "model" to "TNC-X"),
					"APOA??" to mapOf("vendor" to "OpenAPRS", "model" to "app", "class" to "app"),
					"APOCSG" to mapOf("vendor" to "N0AGI", "model" to "POCSAG"),
					"APODOT" to mapOf("vendor" to "Mike, NA7Q", "model" to "ODOT Traffic Alerts", "class" to "service"),
					"APOG7?" to mapOf("vendor" to "OpenGD77", "model" to "OpenGD77"),
					"APOLU?" to mapOf("vendor" to "AMSAT-LU", "model" to "Oscar", "class" to "satellite"),
					"APOPYT" to mapOf("vendor" to "Mike, NA7Q", "model" to "NA7Q Messenger", "class" to "software"),
					"APOSAT" to mapOf("vendor" to "Mike, NA7Q", "model" to "Open Source Satellite Gateway", "class" to "service"),
					"APOSB" to mapOf("vendor" to "SharkRF", "model" to "openSPOT3", "class" to "gadget"),
					"APOSB4" to mapOf("vendor" to "SharkRF", "model" to "openSPOT4", "class" to "gadget"),
					"APOSB?" to mapOf("vendor" to "SharkRF", "model" to ""),
					"APOSBM" to mapOf("vendor" to "SharkRF", "model" to "M1KE", "class" to "gadget"),
					"APOSMS" to mapOf("vendor" to "Mike, NA7Q", "model" to "Open Source SMS Gateway", "class" to "service"),
					"APOSW?" to mapOf("vendor" to "SharkRF", "model" to "openSPOT2", "class" to "gadget"),
					"APOT??" to mapOf("vendor" to "Argent Data Systems", "model" to "OpenTracker", "class" to "tracker"),
					"APOVU?" to mapOf("vendor" to "K J Somaiya Institute", "model" to "BeliefSat"),
					"APOZ??" to mapOf("vendor" to "OZ1EKD, OZ7HVO", "model" to "KissOZ", "class" to "tracker"),
					"APP6??" to mapOf("vendor" to "", "model" to "APRSlib"),
					"APPCO?" to mapOf("vendor" to "RadCommSoft, LLC", "model" to "PicoAPRSTracker", "class" to "tracker"),
					"APPIC?" to mapOf("vendor" to "DB1NTO", "model" to "PicoAPRS", "class" to "tracker"),
					"APPM??" to mapOf("vendor" to "DL1MX", "model" to "rtl-sdr Python iGate", "class" to "software"),
					"APPRIS" to mapOf("vendor" to "DF1JSL", "model" to "Apprise APRS plugin", "class" to "service"),
					"APPS??" to mapOf("vendor" to "Øyvind, LA7ECA (for the Norwegian Radio Relay League)", "model" to "Polaric Server", "class" to "software"),
					"APPT??" to mapOf("vendor" to "JF6LZE", "model" to "KetaiTracker", "class" to "tracker"),
					"APQTH?" to mapOf("vendor" to "Weston Bustraan, W8WJB", "model" to "QTH.app", "class" to "software"),
					"APR2MF" to mapOf("vendor" to "Mike, DL2MF", "model" to "MF2wxAPRS Tinkerforge gateway", "class" to "wx"),
					"APR8??" to mapOf("vendor" to "Bob Bruninga, WB4APR", "model" to "APRSdos", "class" to "software"),
					"APRARX" to mapOf("vendor" to "Open Source", "model" to "radiosonde_auto_rx", "class" to "software"),
					"APRFG?" to mapOf("vendor" to "RF.Guru", "model" to ""),
					"APRFGB" to mapOf("vendor" to "RF.Guru", "model" to "APRS LoRa Pager"),
					"APRFGD" to mapOf("vendor" to "RF.Guru", "model" to "APRS Digipeater", "class" to "digi"),
					"APRFGH" to mapOf("vendor" to "RF.Guru", "model" to "Hotspot", "class" to "rig"),
					"APRFGI" to mapOf("vendor" to "RF.Guru", "model" to "LoRa APRS iGate", "class" to "igate"),
					"APRFGL" to mapOf("vendor" to "RF.Guru", "model" to "Lora APRS Digipeater", "class" to "digi"),
					"APRFGM" to mapOf("vendor" to "RF.Guru", "model" to "Mobile Radio", "class" to "rig"),
					"APRFGP" to mapOf("vendor" to "RF.Guru", "model" to "Portable Radio", "class" to "ht"),
					"APRFGR" to mapOf("vendor" to "RF.Guru", "model" to "Repeater", "class" to "rig"),
					"APRFGT" to mapOf("vendor" to "RF.Guru", "model" to "LoRa APRS Tracker", "class" to "tracker"),
					"APRFGW" to mapOf("vendor" to "RF.Guru", "model" to "LoRa APRS Weather Station", "class" to "wx"),
					"APRG??" to mapOf("vendor" to "OH2GVE", "model" to "aprsg", "class" to "software"),
					"APRHH?" to mapOf("vendor" to "Steven D. Bragg, KA9MVA", "model" to "HamHud", "class" to "tracker"),
					"APRNOW" to mapOf("vendor" to "Gregg Wonderly, W5GGW", "model" to "APRSNow", "class" to "app"),
					"APRPJU" to mapOf("vendor" to "Piju 9M2PJU", "model" to "9M2PJU Bot", "class" to "daemon"),
					"APRPR?" to mapOf("vendor" to "Robert DM4RW, Peter DL6MAA", "model" to "Teensy RPR TNC", "class" to "tracker"),
					"APRRDZ" to mapOf("vendor" to "DL9RDZ", "model" to "rdzTTGOsonde", "class" to "tracker"),
					"APRRES" to mapOf("vendor" to "xssfox", "model" to "APRS-RepeaterRescue", "class" to "network"),
					"APRRF?" to mapOf("vendor" to "RRF - Réseau des Répéteurs Francophones", "model" to "Tracker for RRF", "class" to "tracker"),
					"APRRT?" to mapOf("vendor" to "RPC Electronics", "model" to "RTrak", "class" to "tracker"),
					"APRS" to mapOf("vendor" to "Unknown", "model" to "APRS"),
					"APRX??" to mapOf("vendor" to "Kenneth W. Finnegan, W6KWF", "model" to "Aprx", "class" to "igate"),
					"APS???" to mapOf("vendor" to "Brent Hildebrand, KH2Z", "model" to "APRS+SA", "class" to "software"),
					"APSAR" to mapOf("vendor" to "ZL4FOX", "model" to "SARTrack", "class" to "software"),
					"APSC??" to mapOf("vendor" to "OH2MQK, OH7LZB", "model" to "aprsc", "class" to "software"),
					"APSF??" to mapOf("vendor" to "F5OPV, SFCP_LABS", "model" to "embedded APRS devices"),
					"APSFLG" to mapOf("vendor" to "F5OPV, SFCP_LABS", "model" to "LoRa/APRS Gateway", "class" to "digi"),
					"APSFRP" to mapOf("vendor" to "F5OPV, SFCP_LABS", "model" to "VHF/UHF Repeater"),
					"APSFTL" to mapOf("vendor" to "F5OPV, SFCP_LABS", "model" to "LoRa/APRS Telemetry Reporter"),
					"APSFWX" to mapOf("vendor" to "F5OPV, SFCP_LABS", "model" to "embedded Weather Station", "class" to "wx"),
					"APSK63" to mapOf("vendor" to "Chris Moulding, G4HYG", "model" to "APRS Messenger", "class" to "software"),
					"APSMS?" to mapOf("vendor" to "Paul Dufresne", "model" to "SMS gateway", "class" to "software"),
					"APSRF?" to mapOf("vendor" to "SoftRF", "model" to "Ham Edition", "class" to "tracker"),
					"APSTM?" to mapOf("vendor" to "W7QO", "model" to "Balloon tracker", "class" to "tracker"),
					"APSTPO" to mapOf("vendor" to "N0AGI", "model" to "Satellite Tracking and Operations", "class" to "software"),
					"APSVX?" to mapOf("vendor" to "Tobias Blomberg, SM0SVX", "model" to "SvxLink", "class" to "daemon"),
					"APT2??" to mapOf("vendor" to "Byonics", "model" to "TinyTrak2", "class" to "tracker"),
					"APT3??" to mapOf("vendor" to "Byonics", "model" to "TinyTrak3", "class" to "tracker"),
					"APT4??" to mapOf("vendor" to "Byonics", "model" to "TinyTrak4", "class" to "tracker"),
					"APTB??" to mapOf("vendor" to "BG5HHP", "model" to "TinyAPRS"),
					"APTCHE" to mapOf("vendor" to "PU3IKE", "model" to "TcheTracker, Tcheduino", "class" to "tracker"),
					"APTCMA" to mapOf("vendor" to "Cleber, PU1CMA", "model" to "CAPI Tracker", "class" to "tracker"),
					"APTEMP" to mapOf("vendor" to "KL7AF", "model" to "APRS-Tempest Weather Gateway", "class" to "wx"),
					"APTHUR" to mapOf("vendor" to "YD0BCX", "model" to "APRSThursday weekly event mapbot daemon", "class" to "service"),
					"APTKJ?" to mapOf("vendor" to "W9JAJ", "model" to "ATTiny APRS Tracker"),
					"APTLVC" to mapOf("vendor" to "TA5LVC", "model" to "TR80 APRS Tracker", "class" to "tracker"),
					"APTNG?" to mapOf("vendor" to "Filip YU1TTN", "model" to "Tango Tracker", "class" to "tracker"),
					"APTPN?" to mapOf("vendor" to "KN4ORB", "model" to "TARPN Packet Node Tracker", "class" to "tracker"),
					"APTR??" to mapOf("vendor" to "Motorola", "model" to "MotoTRBO"),
					"APTSLA" to mapOf("vendor" to "HA2NON", "model" to "tesla-aprs", "class" to "daemon"),
					"APTT?" to mapOf("vendor" to "Byonics", "model" to "TinyTrak", "class" to "tracker"),
					"APTW??" to mapOf("vendor" to "Byonics", "model" to "WXTrak", "class" to "wx"),
					"APU1??" to mapOf("vendor" to "Roger Barker, G4IDE", "model" to "UI-View16", "class" to "software"),
					"APU2?" to mapOf("vendor" to "Roger Barker, G4IDE", "model" to "UI-View32", "class" to "software"),
					"APU2??" to mapOf("vendor" to "Roger Barker, G4IDE", "model" to "UI-View32", "class" to "software"),					
					"APUDR?" to mapOf("vendor" to "NW Digital Radio", "model" to "UDR"),
					"APVE??" to mapOf("vendor" to "unknown", "model" to "EchoLink"),
					"APVM??" to mapOf("vendor" to "Digital Radio China Club", "model" to "DRCC-DVM", "class" to "igate"),
					"APVR??" to mapOf("vendor" to "unknown", "model" to "IRLP"),
					"APW9??" to mapOf("vendor" to "Mile Strk, 9A9Y", "model" to "WX Katarina", "class" to "wx"),
					"APWA??" to mapOf("vendor" to "KJ4ERJ", "model" to "APRSISCE", "class" to "software"),
					"APWEE?" to mapOf("vendor" to "Tom Keffer and Matthew Wall", "model" to "WeeWX Weather Software", "class" to "software"),
					"APWM??" to mapOf("vendor" to "KJ4ERJ", "model" to "APRSISCE", "class" to "software"),
					"APWW??" to mapOf("vendor" to "KJ4ERJ", "model" to "APRSIS32", "class" to "software"),
					"APW???" to mapOf("vendor" to "Sproul Brothers", "model" to "WinAPRS", "class" to "software"),
					"APX???" to mapOf("vendor" to "Open Source", "model" to "Xastir", "class" to "software"),
					"APXR??" to mapOf("vendor" to "G8PZT", "model" to "Xrouter"),
					"APY01D" to mapOf("vendor" to "Yaesu", "model" to "FT1D", "class" to "ht"),
					"APY02D" to mapOf("vendor" to "Yaesu", "model" to "FT2D", "class" to "ht"),
					"APY05D" to mapOf("vendor" to "Yaesu", "model" to "FT5D", "class" to "ht"),
					"APY200" to mapOf("vendor" to "Yaesu", "model" to "FTM-200D", "class" to "rig"),
					"APY300" to mapOf("vendor" to "Yaesu", "model" to "FTM-300D", "class" to "rig"),
					"APY400" to mapOf("vendor" to "Yaesu", "model" to "FTM-400", "class" to "rig"),
					"APY500" to mapOf("vendor" to "Yaesu", "model" to "FTM-500D", "class" to "rig"),
					"APYS??" to mapOf("vendor" to "W2GMD", "model" to "Python APRS", "class" to "software"),
					"APZ?" to mapOf("vendor" to "Unknown", "model" to "Experimental"),
					"APZ18" to mapOf("vendor" to "IW3FQG", "model" to "UIdigi", "class" to "digi"),
					"APZ186" to mapOf("vendor" to "IW3FQG", "model" to "UIdigi", "class" to "digi"),
					"APZ19" to mapOf("vendor" to "IW3FQG", "model" to "UIdigi", "class" to "digi"),
					"APZ247" to mapOf("vendor" to "NR0Q", "model" to "UPRS"),
					"APZG??" to mapOf("vendor" to "OH2GVE", "model" to "aprsg", "class" to "software"),
					"APZMAJ" to mapOf("vendor" to "M1MAJ", "model" to "DeLorme inReach Tracker"),
					"APZMDR" to mapOf("vendor" to "Open Source", "model" to "HaMDR", "class" to "tracker"),
					"APZTKP" to mapOf("vendor" to "Nick Hanks, N0LP", "model" to "TrackPoint", "class" to "tracker"),
					"APZWKR" to mapOf("vendor" to "GM1WKR", "model" to "NetSked", "class" to "software"),
					"AP???D" to mapOf("vendor" to "Painter Engineering", "model" to "uSmartDigi D-Gate", "class" to "dstar"),
					"AP???U" to mapOf("vendor" to "Painter Engineering", "model" to "uSmartDigi Digipeater", "class" to "digi"),
					"BEACON" to mapOf("vendor" to "Unknown", "model" to "BEACON"),	
					"ID" to mapOf("vendor" to "Unknown", "model" to "ID"),					
					"PSKAPR" to mapOf("vendor" to "Open Source", "model" to "PSKmail", "class" to "software")
		)
									
		// Kotlin equivalent of process_tocall
		fun processTocall(tocall: String): String? {
			println("Input: $tocall")

			// 1. Check for exact match in TOCALL_DATA
			if (TOCALL_DATA.containsKey(tocall)) {
				println("Exact match found.")
				// Cast the value to a Map and then get the "model" value as a String
				return (TOCALL_DATA[tocall] as? Map<String, Any>)?.get("model") as? String
			}

			// 2. Find the best match with wildcard ("?") in the key
			val bestMatchTocall = TOCALL_DATA.keys
				.filter { it.contains("?") && it.length == tocall.length && tocall.startsWith(it.dropLast(it.count { char -> char == '?' })) }
				.maxByOrNull { it.dropLast(it.count { char -> char == '?' }).length }

			return bestMatchTocall?.let { 
				// Cast the value to a Map and get the "model" value as a String
				(TOCALL_DATA[it] as? Map<String, Any>)?.get("model") as? String
			} ?: null // Return null if no match found, instead of returning tocall
		}


        // Function to check if the last 2 characters of the comment match anything in COMMENT_DATA
        fun micetocall(comment: String): String? {
            val lastTwoChars = comment.takeLast(2) // Get the last 2 characters of the comment
            return COMMENT_DATA[lastTwoChars]?.get("model")
        }

        // Function to check if the last 2 characters of the comment match anything in COMMENT_DATA
        fun kenwoodtocall(comment: String): String? {
			val lastChar = comment.takeLast(1) // Get the last character of the comment
            return KENWOOD_COMMENT_DATA[lastChar]?.get("model")
        }
		
		fun oldkenwoodtocall(packet: String): String? {
			val colonIndex = packet.indexOf(':')
			if (colonIndex == -1 || colonIndex + 10 >= packet.length) {
				return null
			}

			return if (packet[colonIndex + 1] == '\'') {
				val keyChar = packet[colonIndex + 10]
				KENWOOD_COMMENT_DATA[keyChar.toString()]?.get("model")
			} else {
				null
			}
		}
		
		// Function to get packet type based on the type character from the packet
		fun getPacketType(packet: String): String {
			// Get the character after ':' (if exists), otherwise return "Other"
			val typeChar = packet.split(":").getOrNull(1)?.firstOrNull()

			// If no character after ':', return "Other"
			return when (typeChar) {
				'!', '=', '/', '@', '\'', '`' -> "Position"  // Includes Old Mic-E Data (TM-D700)
				'T' -> "Telemetry"
				'>' -> "Status"
				'?' -> "Query"
				';' -> "Object"
				':' -> "Message"
				'_' -> "Weather Report (No Position)"
				'$' -> "Raw GPS Data"
				'}' -> "Third-Party Traffic"
				else -> "Other"  // For any other cases, default to "Other"
			}
		}

		// Helper function to handle Status type and extract the comment
		fun handleStatus(packet: String): String {
			// Extract the content after the first ">" if it exists
			val regex = "^(?:[^>]*>){2}(.*)".toRegex()
			val matchResult = regex.find(packet)
			
			// Return the extracted content or the existing comment if no content is found
			return matchResult?.groups?.get(1)?.value ?: ""
		}

		fun handleTelemetry(packet: String): String {
			// Extract telemetry data from the packet
			// Example: Process telemetry values, strip unnecessary data, etc.
			return packet.substringAfter(":").trim()  // Example: Extract everything after ":"
		}
		
		// Function to split the packet at the first ">" and return the part after it
		fun getSource(packet: String): String? {
			return packet.split(">").getOrNull(0)?.trim()
		}

		fun handleOther(packet: String): String {
			// Extracts the comment/data from "Other" packets
			// If the packet contains a colon ":", extract everything after it.
			return packet.substringAfter(":", "").trim()  // Default to empty if no colon is found
		}

		fun parseComment(comment: String): String {
			var modifiedComment = comment

			// Step 1: Check if it ends with micetocall characters (last 2 characters)
			if (modifiedComment.length >= 2 && AprsPacket.micetocall(modifiedComment.takeLast(2)) != null) {
				return modifiedComment.dropLast(2)  // Strip last 2 characters and return immediately
			}

			// Step 2: Check if it ends with kenwoodtocall (last 1 character)
			if (modifiedComment.isNotEmpty() && AprsPacket.kenwoodtocall(modifiedComment.takeLast(1)) != null) {
				modifiedComment = modifiedComment.dropLast(1)  // Strip last 1 character
			}

			// Step 3: Remove up to 3 characters before and including `}` if it appears in the first 4 characters
			//val bracketIndex = modifiedComment.indexOf("}")
			//if (bracketIndex in 0..3) {
			//	val removeIndex = maxOf(0, bracketIndex - 3)  // Ensure we don't go negative
			//	modifiedComment = modifiedComment.removeRange(removeIndex..bracketIndex).trim()
			//}

			// Step 3: Remove `}` and everything before it if it appears within the first 4 characters
			val bracketIndex = modifiedComment.indexOf("}")
			if (bracketIndex in 0..3) {
				modifiedComment = modifiedComment.substring(bracketIndex + 1).trim()
			}

			// Step 3: Remove specific prefixes ("PHGxxxx", "RNGxxxx", "DFSxxxx")
			//val prefixPatterns = listOf("^PHG\\d{4}", "^RNG\\d{4}", "^DFS\\d{4}")		
			val prefixPatterns = listOf(
				"PHG\\d{4}/?",  // Matches PHG followed by exactly 4 digits, with an optional trailing slash
				"RNG\\d{4}/?",  // Matches RNG followed by exactly 4 digits, with an optional trailing slash
				"DFS\\d{4}/?"   // Matches DFS followed by exactly 4 digits, with an optional trailing slash
			)			
			
			for (pattern in prefixPatterns) {
				modifiedComment = modifiedComment.replaceFirst(pattern.toRegex(), "").trim()
			}

			// Step 4: Remove altitude format "/A=XXXXX" where X can be positive or negative digits
			//val altitudePattern = "/A=-?\\d{6}".toRegex()
			val altitudePattern = "/?A=(-\\d{5}|\\d{6})".toRegex()	
			modifiedComment = modifiedComment.replace(altitudePattern, "").trim()

			// Step 5: Remove "XXX/YYY" or "XXX/YYY/A=ZZZZZ" format
			//val coursespeedPattern = "^\\d{3}/\\d{3}(/A=-?\\d{6})?".toRegex()
			val coursespeedPattern = "^\\d{3}/\\d{3}(/A=(-?\\d{5}|\\d{6}))?/?".toRegex()
			
			modifiedComment = modifiedComment.replace(coursespeedPattern, "").trim()

/* 			// Step 6: Remove Weather & Telemetry Data
			val weatherPatterns = listOf(
				"\\.\\.\\./\\.\\.\\.",  // Matches ".../..." (Direction/Speed missing)
				"\\.\\.\\./\\d{3}",    // Matches ".../XXX" (Speed missing)
				"\\d{3}/\\.\\.\\.",    // Matches "XXX/..." (Direction missing)				
				"c[\\d.]{3}",  // Course (cXXX or c...)
				"s[\\d.]{3}",  // Speed (sXXX or s...)				
				"g[\\d.]{3}",  // Wind Gust (gXXX or g...)				
				"t[\\d.]{3}",  // Temperature (tXXX or t...)
				"r[\\d.]{3}",  // Rainfall in last hour (rXXX or r...)
				"p[\\d.]{3}",  // Rainfall in last 24 hours (pXXX or p...)
				"P[\\d.]{3}",  // Rainfall since midnight (PXXX or P...)
				"h[\\d.]{2,3}", // Humidity (hXX, hXXX or h..)
				"b[\\d.]{5}",  // Barometric Pressure (bXXXXX or b.....)
				"L[\\d.]{3}"  // Luminosity (LXXX or L...)
				 */
				 
			val weatherPatterns = listOf(
				"\\.\\.\\./\\.\\.\\.",  // Matches ".../..." (Direction/Speed missing)
				"\\.\\.\\./\\d{3}",    // Matches ".../XXX" (Speed missing)
				"\\d{3}/\\.\\.\\.",    // Matches "XXX/..." (Direction missing)				
				"c\\d{3}",  // Course (cXXX)
				"c\\.\\.\\.",  // Course missing (c...)
				"s\\d{3}",  // Speed (sXXX)
				"s\\.\\.\\.",  // Speed missing (s...)				
				"g\\d{3}",  // Wind Gust (gXXX)
				"g\\.\\.\\.",  // Wind Gust missing (g...)				
				"t\\d{3}",  // Temperature (tXXX)
				"t\\.\\.\\.",  // Temperature missing (t...)
				"r\\d{3}",  // Rainfall in last hour (rXXX)
				"r\\.\\.\\.",  // Rainfall missing (r...)
				"p\\d{3}",  // Rainfall in last 24 hours (pXXX)
				"p\\.\\.\\.",  // Rainfall missing (p...)
				"P\\d{3}",  // Rainfall since midnight (PXXX)
				"P\\.\\.\\.",  // Rainfall missing (P...)
				"h\\d{2,3}", // Humidity (hXX or hXXX)
				"h\\.{2,3}",  // Humidity missing (h.. or h...)
				"b\\d{5}",  // Barometric Pressure (bXXXXX)
				"b\\.\\.\\.\\.\\.",  // Barometric Pressure missing (b.....)
				"L\\d{3}",  // Luminosity (LXXX)
				"L\\.\\.\\."   // Luminosity missing (L...)
			)
			
			for (pattern in weatherPatterns) {
				modifiedComment = modifiedComment.replace(pattern.toRegex(), "").trim()
			}
			
			// Return the modified comment after all transformations
			return modifiedComment
		}
	}
}
