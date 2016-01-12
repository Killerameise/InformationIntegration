using System;
using System.Collections.Generic;

namespace InfoInteSourceCleansing
{
    public partial class occupation_sportsman
    {
        public int occupation_id { get; set; }
        public int sportsman_id { get; set; }

        public virtual Occupation occupation { get; set; }
        public virtual sportsman sportsman { get; set; }
    }
}
