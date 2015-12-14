using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class rankings
    {
        public int id { get; set; }
        public int? date { get; set; }
        public int? player_id { get; set; }
        public int? pos { get; set; }
        public int? pts { get; set; }

        public virtual players player { get; set; }
    }
}
