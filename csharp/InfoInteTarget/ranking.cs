using System;
using System.Collections.Generic;

namespace InfoInteTarget
{
    public partial class ranking
    {
        public int id { get; set; }
        public int? date { get; set; }
        public int? position { get; set; }
        public int? pts { get; set; }
        public int? sportsman_id { get; set; }

        public virtual sportsman sportsman { get; set; }
    }
}
