using System;
using System.Collections.Generic;

namespace InfoInteSourceCleansing
{
    public partial class liga
    {
        public liga()
        {
            club = new HashSet<club>();
        }

        public int id { get; set; }
        public DateTime? erstaustragung { get; set; }
        public int? meister { get; set; }
        public string rekordspieler { get; set; }
        public int? spiele_rekordspieler { get; set; }
        public string verband { get; set; }

        public virtual ICollection<club> club { get; set; }
        public virtual club meisterNavigation { get; set; }
    }
}
