using System;
using System.Collections.Generic;

namespace InfoInteSource
{
    public partial class people
    {
        public people()
        {
            people_ref = new HashSet<people_ref>();
        }

        public string person_id { get; set; }
        public int? associated_article_count { get; set; }
        public string attribution_name { get; set; }
        public string attribution_url { get; set; }
        public DateTime? created { get; set; }
        public string creator { get; set; }
        public string definition { get; set; }
        public DateTime? first_use { get; set; }
        public string in_scheme { get; set; }
        public DateTime? latest_use { get; set; }
        public string license { get; set; }
        public string mapping_strategy { get; set; }
        public DateTime? modified { get; set; }
        public int? number_of_variants { get; set; }
        public string pref_label { get; set; }
        public string primary_topic { get; set; }
        public string rightsholder { get; set; }
        public string same_as { get; set; }
        public string search_api_query { get; set; }
        public string topicpage { get; set; }
        public string type { get; set; }

        public virtual ICollection<people_ref> people_ref { get; set; }
    }
}
