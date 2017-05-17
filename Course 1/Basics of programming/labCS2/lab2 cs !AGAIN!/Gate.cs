using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2_cs__AGAIN_
{
    class Gate
    {
        public enum TypeOfGate { AND = 1, OR, NOR, NOT };
        public TypeOfGate typeOfGate { get; private set; }
        private bool inputValue1, inputValue2, outputValue1;

        public Gate (bool inputValue1, bool inputValue2, int typeOfGate)
        {
            this.inputValue1 = inputValue1;
            this.inputValue2 = inputValue2;

            switch (typeOfGate)
            {
                case 1:
                    this.typeOfGate = TypeOfGate.AND;
                    outputValue1 = inputValue1 & inputValue2;
                    break;
                case 2:
                    this.typeOfGate = TypeOfGate.OR;
                    outputValue1 = inputValue1 | inputValue2;
                    break;
                case 3:
                    this.typeOfGate = TypeOfGate.NOR;
                    outputValue1 = inputValue1 ^ inputValue2;
                    break;
                case 4:
                    this.typeOfGate = TypeOfGate.NOT;
                    outputValue1 = !inputValue1;
                    break;
                
            }
        }

        public string ObjectConclusion()
        {
            if ((int)typeOfGate != 4)
            {
                string str = System.String.Format("{0} {1} {2} = {3} ", inputValue1, typeOfGate, inputValue2, outputValue1);
                return str;
            }
            else
            {
                string str = System.String.Format("{0} {1} = {2} ", typeOfGate, inputValue1,  outputValue1);
                return str;
            }
        }

    }


}
