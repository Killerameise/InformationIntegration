using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class people_ref
    {
        public string article_id { get; set; }
        public string person_id { get; set; }

        public virtual article_header article { get; set; }
        public virtual people person { get; set; }
    }
}
