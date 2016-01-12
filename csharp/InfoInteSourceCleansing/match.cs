using System;
using System.Collections.Generic;

namespace InfoInteSourceCleansing
{
    public partial class match
    {
        public int id { get; set; }
        public DateTime? date { get; set; }
        public int? foreign { get; set; }
        public int? home { get; set; }
        public int? spieltag { get; set; }
        public TimeSpan? time { get; set; }
        public int? tore_gast { get; set; }
        public int? tore_heim { get; set; }

        public virtual club foreignNavigation { get; set; }
        public virtual club homeNavigation { get; set; }
    }
}
