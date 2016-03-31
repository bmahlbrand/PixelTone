public class GeneticHarmonicProgression{

	Note key;
	Mode mode;
	Cadence cadence;
	Section harmonicProgression;

	public GeneticMotive(){
		key = new Note("C");
		mode = Mode.Ionian;
		cadence = Cadence.Authentic;
		generateGeneticHarmonicProgression(key, mode, cadence);
	}

	public GeneticMotive(Note key, Mode mode, Cadence cadence){
		this.key = key;
		this.mode = mode;
		this.cadence = cadence;

		generateGeneticHarmonicProgression(key, mode, cadence);
	}

	private void generateGeneticHarmonicProgression(Note key, Mode mode){
		CommonChordProgFitFunction fitnessFunction = new CommonChordProgFitFunction(cadence);

		harmonicProgression = phenotypeToSection(GeneticAlgorithm.geneticAlgorithm(1, 7, 8, fitnessFunction));
	}

	public Section getHarmonicProgression(){
		return harmonicProgression;
	}

	//IMPLEMENTING SHORTLY!!!!!
	private Section phenotypeToSection(Phenotype<IntegerGene,Integer> phenotypeMeasure){
		Section thisSection = new Section();


		return thisSection;

	}
}