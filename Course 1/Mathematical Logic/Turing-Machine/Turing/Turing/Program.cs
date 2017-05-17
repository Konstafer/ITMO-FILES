using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace TuringM
{
    class Program
    {
        static void Main(string[] args)
        {
            Dictionary<string, string> table = new Dictionary<string, string>();
            Console.Write("Alphabet: ");

            var stringOfAlphabet = "s0  0   1   2";

            Console.WriteLine(stringOfAlphabet);
            var alphabet = stringOfAlphabet.Split('\t');
            var numbersOfLetters = alphabet.Length;
            Console.Write("The number of states: ");

            var statesNumber = 3;

            Console.WriteLine(statesNumber);

            table = new Dictionary<string, string>()
                {
                    {"1 s0","q0 1 L"},
                    {"1 0","q3 1 R"},
                    {"1 1","q1 2 R"},
                    {"1 2","q3 0 R"},
                

                {"2 s0","q0 2 С"},
                {"2 0","q2 2 R"},
                {"2 1","q1 0 R"},
                {"2 2","q2 1 R"},
               

                {"3 s0","q0 0 R"},
                {"3 0","q2 2 L"},
                {"3 1","q3 2 C"},
                {"3 2","q1 2 L"},
            };

            
            Console.Write("TAPE:");

            var stringWithTape = "s0\t!2\t0\t1\t2\t0\t1\t2\t0\t1\ts0";
            //var stringWithTape = "s0\t!0\t0\ts0";


            Console.WriteLine(stringWithTape);
            List<string> tape = new List<string>();
            tape.AddRange(stringWithTape.Split('\t'));

            
            var head = 0;
            for (int i = 0; i < tape.Count; i++)
            {
                var letter = tape[i];
                if (letter.Contains('!'))
                {
                    head = i;
                    tape[i] = letter.Replace("!", "");
                }
            }

           

            Console.WriteLine("\n\n\n");
            Console.WriteLine(new string('\t', head + 1) + "| q1");
            Console.WriteLine($"p0:\t{stringWithTape.Replace("!", "")}\n");


            //выполнение 
            var currentState = 1;
            var counter = 0;


            while (currentState != 0)
            {

                counter++;
                var currentCommand = table[$"{currentState} {tape[head]}"];
                var parts = currentCommand.Split(' ');

                tape[head] = parts[1];

                switch (parts[2])
                {
                    case "L":
                        if (head == 0)
                            tape.Insert(0, "s0");
                        else
                            head -= 1;

                        break;
                    case "R":
                        if (head == tape.Count - 1)
                            tape.Add("s0");
                        head += 1;
                        break;
                }

                currentState = int.Parse(parts[0].Replace("q", ""));

                Console.WriteLine();

                Console.WriteLine(new string('\t', head + 1) + $"| q{currentState}");

                stringWithTape = string.Join("\t", tape);
                Console.WriteLine($"p{counter}:\t{stringWithTape}\n");

                if (counter > 1000)
                {
                    Console.WriteLine("Бесконечный цикл");
                    break;
                }
            }


            Console.Read();


        }
        static string[] LineOfCommands(Dictionary<string, string> table, int line)
        {
            var Arr = new List<string> { $"q{line + 1}" };
            Arr.AddRange(from command in table where command.Key.Split(' ')[0] == (line + 1).ToString() select command.Value);
            return Arr.ToArray();
        }
    }
}