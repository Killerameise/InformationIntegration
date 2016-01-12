using System;
using System.Collections.Generic;

namespace InfoInteSourceCleansing
{
    public partial class school
    {
        public school()
        {
            college_playing = new HashSet<college_playing>();
        }

        public string id { get; set; }
        public string city { get; set; }
        public string country { get; set; }
        public string name_full { get; set; }
        public string state { get; set; }

        public virtual ICollection<college_playing> college_playing { get; set; }
    }
}
