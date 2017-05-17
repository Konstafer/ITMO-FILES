using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2_cs__AGAIN_
{
    class Gate : IComparable<Gate>, IEquatable<Gate>
    {

        public int typeOfGate { get; protected set; }
        protected bool inputValue1, inputValue2, outputValue1;

        public Gate(bool inputValue1, bool inputValue2)
        {
            this.inputValue1 = inputValue1;
            this.inputValue2 = inputValue2;
        }

        public override string ToString()
        {
            if (typeOfGate != 4)
            {
                string str = String.Format("{0} {1} {2} = {3} ", inputValue1, (char)typeOfGate, inputValue2, outputValue1);
                return str;
            }
            else
            {
                string str = String.Format("{0} {1} = {2} ", (char)typeOfGate, inputValue1, outputValue1);
                return str;
            }
        }

        public virtual int CompareTo(Gate obj)
        {
            if ((Convert.ToInt32(this.outputValue1)) > (Convert.ToInt32(obj.outputValue1)))
                return 1;
            if ((Convert.ToInt32(this.outputValue1)) < (Convert.ToInt32(obj.outputValue1)))
                return -1;
            else
                return 0;
        }

        public bool Equals(Gate obj)
        {
            if (CompareTo(obj) == 0)
                return true;
            return false;
        }

        public int Test(Gate obj)
        {
            if (obj is Gate)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }


    class AND : Gate
    {
        public AND(bool inputValue1, bool inputValue2)
            : base(inputValue1, inputValue2)
        {
            typeOfGate = '&';
            outputValue1 = inputValue1 & inputValue2;
        }

        public override string ToString()
        {
            return base.ToString();
        }
    }

    class OR : Gate
    {
        public OR(bool inputValue1, bool inputValue2)
            : base(inputValue1, inputValue2)
        {
            typeOfGate = '|';
            outputValue1 = inputValue1 | inputValue2;
        }

        public override string ToString()
        {
            return base.ToString();
        }
    }

    class NOR : Gate
    {
        public NOR(bool inputValue1, bool inputValue2)
            : base(inputValue1, inputValue2)
        {
            typeOfGate = '^';
            outputValue1 = inputValue1 ^ inputValue2;
        }

        public override string ToString()
        {
            return base.ToString();
        }
    }
    class NOT : Gate
    {
        public NOT(bool inputValue1, bool inputValue2)
            : base(inputValue1, inputValue2)
        {
            typeOfGate = '!';
            outputValue1 = !inputValue1;
        }

        public override string ToString()
        {
            return base.ToString();
        }
    }
}
