using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class halloffame
    {
        public string playerid { get; set; }
        public int yearid { get; set; }
        public string votedby { get; set; }
        public int? ballots { get; set; }
        public string category { get; set; }
        public string inducted { get; set; }
        public int? needed { get; set; }
        public string needed_note { get; set; }
        public int? votes { get; set; }

        public virtual master playeridNavigation { get; set; }
    }
}
