using System;
using System.Collections.Generic;

namespace InfoInteTarget
{
    public partial class sportsman //=> Lection
    {
        public sportsman()
        {
            college_playing = new HashSet<college_playing>(); // Fremdschlüsselbeziehungen
            fight = new HashSet<fight>();
            hall_of_fame = new HashSet<hall_of_fame>();
            ranking = new HashSet<ranking>();
        }

        public int id { get; set; }                         // Attribut
        public int? Anzahl_Einsaetze { get; set; }
        public int? assist { get; set; }
        public string bats { get; set; }
        public string bbrefID { get; set; }
        public string belt { get; set; }
        public string birthCity { get; set; }
        public string birthCountry { get; set; }
        public DateTime? birthdate { get; set; }
        public string birthState { get; set; }
        public bool? captain { get; set; }
        public int? club_id { get; set; }
        public string coach { get; set; }
        public string comment { get; set; }
        public string contract_until { get; set; }
        public string country { get; set; }
        public string deathCity { get; set; }
        public string deathCountry { get; set; }
        public int? deathDay { get; set; }
        public int? deathMonth { get; set; }
        public string deathState { get; set; }
        public int? deathYear { get; set; }
        public DateTime? debut { get; set; }
        public string enddate { get; set; }
        public string familynamelocal { get; set; }
        public string favoritetechnique { get; set; }
        public DateTime? finalGame { get; set; }
        public string firstname { get; set; }
        public string firstnamelocal { get; set; }
        public int? games { get; set; }
        public string gender { get; set; }
        public int? goals { get; set; }
        public string hand { get; set; }
        public double? height { get; set; }
        public string lastname { get; set; }
        public string market_value { get; set; }
        public string middlename { get; set; }
        public string middlenamelocal { get; set; }
        public string nameGiven { get; set; }
        public string occupation { get; set; }
        public string playername { get; set; }
        public int? playernumber { get; set; }
        public string position { get; set; }
        public string retroID { get; set; }
        public string shortname { get; set; }
        public string side { get; set; }
        public string startdate { get; set; }
        public string throws { get; set; }
        public int? weight { get; set; }
        public string year { get; set; }

        public virtual ICollection<college_playing> college_playing { get; set; }
        public virtual ICollection<fight> fight { get; set; }
        public virtual ICollection<hall_of_fame> hall_of_fame { get; set; }
        public virtual ICollection<ranking> ranking { get; set; }
        public virtual club club { get; set; }
    }
}
