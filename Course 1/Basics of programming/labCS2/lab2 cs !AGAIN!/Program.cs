using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2_cs__AGAIN_
{
    class Program
    {
        
        static void Main(string[] args)
        {
            var gateCollection = new GateCollection();
            int menuItem = 0;
            bool presenceOperation = false;

            while (true)
            {
               
                Menu(ref menuItem, ref presenceOperation);

                switch (menuItem)
                {
                    case 1:
                        Console.Clear();
                        presenceOperation = true;
                        bool inputValue1;
                        bool inputValue2;
                        int typeOfGate;
                        EnterOperation(out inputValue1, out inputValue2, out typeOfGate);
                        gateCollection.AddGate(new Gate(inputValue1, inputValue2, typeOfGate));
                        break;

                    case 2:
                        Console.Clear();
                        for (var i = 0; i < gateCollection.items.Count; i++)
                        {
                            Console.WriteLine(gateCollection.GetItems()[i].ObjectConclusion());
                        }
                        break;

                    case 3:
                        Console.Clear();
                        Console.WriteLine("Enter the type of gate for search\n 1.AND\n 2.OR\n 3.NOR\n 4.NOT");
                        typeOfGate = Int32.Parse(Console.ReadLine());
                        if (typeOfGate <= 0 || typeOfGate > 4)
                        {
                            Console.WriteLine("Incorect choise");
                            continue;
                        }
                        List<Gate> list = new List<Gate>();
                        list = gateCollection.Search(typeOfGate);
                        Console.Clear();
                        foreach (var item in list)
                        {
                            Console.WriteLine(item.ObjectConclusion());
                        }
                        break;
                    case 4:
                        Console.Clear();
                        Console.WriteLine("Ener index operation for removing");
                        int index = Int32.Parse(Console.ReadLine());
                        if (index-1 > gateCollection.items.Count || index <= 0)
                        {
                            Console.WriteLine("Such index is no");
                            continue;
                        }
                        gateCollection.Removal(index);
                        break;

                    default:
                        Environment.Exit(0);
                        break;




                }
            }
        }

        static public void Menu(ref int menuItem, ref bool presenceOperation)
        {
           
            while (true)
            {
                Console.WriteLine("\n 1.Add operation\n 2.Show items\n 3.Search item\n 4.Delete item");
                try
                {
                    menuItem = Int32.Parse(Console.ReadLine());

                    if (menuItem > 1 && menuItem <=4 && presenceOperation == false)
                    {
                        Console.WriteLine("\nNo operations.");
                        continue;
                    }
                    break;
                }

                catch
                {
                    Environment.Exit(0);
                }
            }
        }

        static public void EnterOperation(out bool inputValue1, out bool inputValue2, out int typeOfGate)
        {
            while (true)
            {
                
                Console.WriteLine("Select gate:\n 1.AND\n 2.OR\n 3.NOR\n 4.NOT");

                try
                {
                    typeOfGate = Int32.Parse(Console.ReadLine());
                    if (typeOfGate <= 0 || typeOfGate > 4)
                    {
                        Console.WriteLine("Incorect choise");
                        continue;
                    }
                    EnterValue(out inputValue1, out inputValue2, ref typeOfGate);
                    break;
                }

                catch
                {
                    Console.WriteLine(" - Incorect choise.");
                    continue;
                }
            }

        }

        static public void EnterValue(out bool inputValue1, out bool inputValue2, ref int typeOfGate)
        {
            ConsoleKeyInfo button;
            bool flag = false;
            inputValue1 = false;
            inputValue2 = false;

            Console.Clear();

            while (true)
            { 
                if (typeOfGate == 4)
                {
                    Console.WriteLine("\nEnter the input values\n (Press T if True, F if False)");
                    button = Console.ReadKey();

                    if (button.Key == ConsoleKey.T)
                    {
                        inputValue1 = true;
                        Console.Clear();
                        break;
                    }

                    if (button.Key == ConsoleKey.F)
                    {
                        inputValue1 = false;
                        Console.Clear();
                        break;
                    }
                    else
                    {
                        throw new Exception();
                    }
                }

                else
                {
                    Console.WriteLine("\nEnter the input values \n (Press T if True, F if False)");
                    button = Console.ReadKey();
                    if (flag == false)
                    {
                        flag = true;

                        if (button.Key == ConsoleKey.T)
                        {
                            inputValue1 = true;
                            continue;
                        }

                        if (button.Key == ConsoleKey.F)
                        {
                            inputValue1 = false;
                            continue;
                        }
                        else
                        {
                            throw new Exception();
                        }
                    }
                    else
                    {
                        if (button.Key == ConsoleKey.T)
                        {
                            inputValue2 = true;
                            Console.Clear();
                            break;
                        }

                        if (button.Key == ConsoleKey.F)
                        {
                            inputValue2 = false;
                            Console.Clear();
                            break;
                        }
                        else
                        {
                            throw new Exception();
                        }
                    }
                }
            }
        }


    }
}
