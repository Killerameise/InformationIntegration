using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class article_header
    {
        public article_header()
        {
            organizations_ref = new HashSet<organizations_ref>();
            people_ref = new HashSet<people_ref>();
        }

        public string article_id { get; set; }
        public string _abstract { get; set; }
        public string document_type { get; set; }
        public string headline { get; set; }
        public string print_page { get; set; }
        public DateTime? pub_date { get; set; }
        public string source { get; set; }
        public string web_url { get; set; }
        public int? word_count { get; set; }

        public virtual ICollection<organizations_ref> organizations_ref { get; set; }
        public virtual ICollection<people_ref> people_ref { get; set; }
    }
}
