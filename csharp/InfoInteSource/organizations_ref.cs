using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class organizations_ref
    {
        public string article_id { get; set; }
        public string organization_id { get; set; }

        public virtual article_header article { get; set; }
        public virtual organizations organization { get; set; }
    }
}
