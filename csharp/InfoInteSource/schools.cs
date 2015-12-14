using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class schools
    {
        public schools()
        {
            collegeplaying = new HashSet<collegeplaying>();
        }

        public string schoolid { get; set; }
        public string city { get; set; }
        public string country { get; set; }
        public string name_full { get; set; }
        public string state { get; set; }

        public virtual ICollection<collegeplaying> collegeplaying { get; set; }
    }
}
