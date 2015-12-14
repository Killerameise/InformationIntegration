using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class players
    {
        public players()
        {
            rankings = new HashSet<rankings>();
        }

        public int id { get; set; }
        public int? birth { get; set; }
        public string country { get; set; }
        public string firstname { get; set; }
        public string hand { get; set; }
        public string lastname { get; set; }

        public virtual ICollection<rankings> rankings { get; set; }
    }
}
