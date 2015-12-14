using System;
using System.Collections.Generic;

namespace InfoInteTarget
{
    public partial class college_playing
    {
        public int sportsman_id { get; set; }
        public string school_id { get; set; }
        public int year { get; set; }

        public virtual school school { get; set; }
        public virtual sportsman sportsman { get; set; }
    }
}
