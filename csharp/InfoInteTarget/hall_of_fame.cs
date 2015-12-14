using System;
using System.Collections.Generic;

namespace InfoInteTarget
{
    public partial class hall_of_fame
    {
        public int sportsman_id { get; set; }
        public int year { get; set; }
        public string voted_by { get; set; }
        public int? ballots { get; set; }
        public string category { get; set; }
        public string inducted { get; set; }
        public int? needed { get; set; }
        public string needed_note { get; set; }
        public int? votes { get; set; }

        public virtual sportsman sportsman { get; set; }
    }
}
