using System;
using System.Collections.Generic;

namespace InfoInteSourceCleansing
{
    public partial class club
    {
        public club()
        {
            liga1 = new HashSet<liga>();
            match = new HashSet<match>();
            matchNavigation = new HashSet<match>();
            sportsman = new HashSet<sportsman>();
        }

        public int id { get; set; }
        public int? liga { get; set; }
        public string name { get; set; }

        public virtual ICollection<liga> liga1 { get; set; }
        public virtual ICollection<match> match { get; set; }
        public virtual ICollection<match> matchNavigation { get; set; }
        public virtual ICollection<sportsman> sportsman { get; set; }
        public virtual liga ligaNavigation { get; set; }
    }
}
