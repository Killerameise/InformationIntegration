using System;
using System.Collections.Generic;

namespace InfoInteSourceCleansing
{
    public partial class team
    {
        public team()
        {
            league_table = new HashSet<league_table>();
        }

        public int id { get; set; }
        public string code { get; set; }
        public string crest_url { get; set; }
        public float? defrtg { get; set; }
        public int? drebfor { get; set; }
        public int? drebopp { get; set; }
        public float? drebrate { get; set; }
        public string group { get; set; }
        public float? min { get; set; }
        public string name { get; set; }
        public float? offrtg { get; set; }
        public int? orebfor { get; set; }
        public int? orebopp { get; set; }
        public float? orebrate { get; set; }
        public float? overallrtg { get; set; }
        public int? pointopp { get; set; }
        public int? pointsfor { get; set; }
        public int? possfor { get; set; }
        public int? possopp { get; set; }
        public string short_name { get; set; }
        public string squad_market_value { get; set; }

        public virtual ICollection<league_table> league_table { get; set; }
    }
}
