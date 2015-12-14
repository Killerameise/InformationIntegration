using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class collegeplaying
    {
        public string playerid { get; set; }
        public string schoolid { get; set; }
        public int yearid { get; set; }

        public virtual master playeridNavigation { get; set; }
        public virtual schools schoolidNavigation { get; set; }
    }
}
