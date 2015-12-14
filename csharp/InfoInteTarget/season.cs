using System;
using System.Collections.Generic;

namespace InfoInteTarget
{
    public partial class season
    {
        public int id { get; set; }
        public string caption { get; set; }
        public string last_updated { get; set; }
        public string league { get; set; }
        public int? league_table { get; set; }
        public int? number_of_teams { get; set; }
        public int? year { get; set; }

        public virtual league_table league_tableNavigation { get; set; }
    }
}
