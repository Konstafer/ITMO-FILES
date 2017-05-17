using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2_cs__AGAIN_
{
    class GateCollection
    {
        public List<Gate> items;
        

        public GateCollection()
        {
            items = new List<Gate>();
        }

         public void AddGate(Gate item)
        {
            items.Add(item);
        }
        
        public List<Gate> GetItems()
        {
            return items;
        }

        public List<Gate> Search(int typeOfGate)
        {
            List<Gate> copyItems = new List<Gate>();

            foreach (var item in items)
            {
                if (typeOfGate == (int)item.typeOfGate)
                {
                    copyItems.Add(item);
                }
            }

            return copyItems;
        }

        public void Removal(int index)
        {
           items.RemoveAt(index-1);
        }

    }
}
