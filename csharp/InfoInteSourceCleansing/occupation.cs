using System;
using System.Collections.Generic;

namespace InfoInteSourceCleansing
{
    public partial class Occupation
    {
        public Occupation()
        {
            occupation_sportsman = new HashSet<occupation_sportsman>();
        }

        public int id { get; set; }
        public string occupation { get; set; }

        public virtual ICollection<occupation_sportsman> occupation_sportsman { get; set; }
    }
}
